package com.example.mockito.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 5: Mocking and Stubbing with Multiple Return Values
 *
 * Key idea: some workflows call the same method repeatedly and need
 * different results each time – for example:
 *   • Paginated data loading
 *   • Retry logic (fail first, succeed later)
 *   • State transitions modelled as consecutive reads
 *
 * Mockito idioms shown:
 *   when(mock.method()).thenReturn(v1).thenReturn(v2)   ← chained
 *   when(mock.method()).thenReturn(v1, v2, v3)           ← vararg shorthand
 *   when(mock.method()).thenReturn(v1).thenThrow(ex)     ← mixed
 *
 * After all values are consumed, the LAST one repeats indefinitely.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 5: Multiple Return Values")
class MultiReturnServiceTest {

    @Mock
    Repository mockRepository;

    @InjectMocks
    Service service;

    // ---------------------------------------------------------------
    // 5a. Core scenario from the exercise brief (chained thenReturn)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Consecutive processData() calls return different values")
    void testServiceWithMultipleReturnValues() {
        // Arrange – first call returns "First Mock Data", second returns "Second Mock Data"
        when(mockRepository.getData())
                .thenReturn("First Mock Data")
                .thenReturn("Second Mock Data");

        // Act
        String firstResult  = service.processData();
        String secondResult = service.processData();

        // Assert
        assertEquals("Processed First Mock Data",  firstResult);
        assertEquals("Processed Second Mock Data", secondResult);
    }

    // ---------------------------------------------------------------
    // 5b. Vararg shorthand – three consecutive values
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Vararg thenReturn supplies three distinct values in order")
    void varargThenReturn_ThreeValues() {
        when(mockRepository.getData())
                .thenReturn("Alpha", "Beta", "Gamma");

        assertEquals("Processed Alpha", service.processData());
        assertEquals("Processed Beta",  service.processData());
        assertEquals("Processed Gamma", service.processData());
    }

    // ---------------------------------------------------------------
    // 5c. Last value repeats after all are consumed
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Last stubbed value repeats when more calls are made than stubs provided")
    void lastValueRepeatsAfterStubsExhausted() {
        when(mockRepository.getData()).thenReturn("Only", "Last");

        // 4 calls but only 2 stubs → "Last" repeats
        assertEquals("Processed Only", service.processData());
        assertEquals("Processed Last", service.processData());
        assertEquals("Processed Last", service.processData()); // repeat
        assertEquals("Processed Last", service.processData()); // repeat

        verify(mockRepository, times(4)).getData();
    }

    // ---------------------------------------------------------------
    // 5d. Mixed thenReturn + thenThrow  (retry simulation)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("First call succeeds; second simulates a transient DB failure")
    void firstSuccessSecondThrows() {
        when(mockRepository.getData())
                .thenReturn("Good Data")
                .thenThrow(new RuntimeException("DB connection lost"));

        // First call – succeeds
        assertEquals("Processed Good Data", service.processData());

        // Second call – DB failure propagates
        assertThrows(RuntimeException.class, () -> service.processData());
    }

    // ---------------------------------------------------------------
    // 5e. Simulate paginated loading (fetchMultiple equivalent)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Fetching multiple records returns each page's data in order")
    void paginatedFetch_ReturnsDifferentDataPerCall() {
        when(mockRepository.getData())
                .thenReturn("Page1Record", "Page2Record", "Page3Record");

        // Manually call processData three times (simulates page iteration)
        List<String> pages = List.of(
            service.processData(),
            service.processData(),
            service.processData()
        );

        assertEquals(
            List.of("Processed Page1Record",
                    "Processed Page2Record",
                    "Processed Page3Record"),
            pages
        );
    }

    // ---------------------------------------------------------------
    // 5f. Different return values for different arguments (per-ID stubs)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Different IDs can be stubbed to return different results")
    void differentStubsPerArgument() {
        when(mockRepository.findById(1)).thenReturn(java.util.Optional.of("Record-1"));
        when(mockRepository.findById(2)).thenReturn(java.util.Optional.of("Record-2"));
        when(mockRepository.findById(99)).thenReturn(java.util.Optional.empty());

        assertEquals("Processed Record-1",  service.processById(1));
        assertEquals("Processed Record-2",  service.processById(2));
        assertEquals("Record 99 not found", service.processById(99));
    }
}
