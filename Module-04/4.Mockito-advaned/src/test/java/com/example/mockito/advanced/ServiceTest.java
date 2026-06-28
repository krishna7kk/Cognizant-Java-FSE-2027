package com.example.mockito.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 1: Mocking Databases and Repositories
 *
 * Key idea: a real Repository hits a database.
 * By replacing it with a Mockito mock we get tests that are:
 *   • Instant  – no DB connection overhead
 *   • Isolated – only Service logic is tested, not SQL
 *   • Reliable – no flaky data state in the schema
 *
 * New annotation shown here:
 *   @InjectMocks – Mockito creates the class under test AND injects
 *                  all @Mock / @Spy fields into it automatically
 *                  (via constructor, setter, or field injection).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 1: Mocking Databases and Repositories")
class ServiceTest {

    @Mock
    Repository mockRepository;

    @InjectMocks          // Mockito calls new Service(mockRepository)
    Service service;

    // ---------------------------------------------------------------
    // 1a. Core scenario from the exercise brief
    // ---------------------------------------------------------------

    @Test
    @DisplayName("processData() prefixes repo data with 'Processed '")
    void testServiceWithMockRepository() {
        // Arrange – stub the repo to return a known value
        when(mockRepository.getData()).thenReturn("Mock Data");

        // Act
        String result = service.processData();

        // Assert
        assertEquals("Processed Mock Data", result,
            "Service should prepend 'Processed ' to whatever the repo returns");
    }

    // ---------------------------------------------------------------
    // 1b. findById – record found
    // ---------------------------------------------------------------

    @Test
    @DisplayName("processById() returns 'Processed <data>' when record exists")
    void processById_RecordFound() {
        when(mockRepository.findById(10))
                .thenReturn(Optional.of("User Alice"));

        String result = service.processById(10);

        assertEquals("Processed User Alice", result);
        verify(mockRepository).findById(10);
    }

    // ---------------------------------------------------------------
    // 1c. findById – record absent (Optional.empty)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("processById() returns fallback message when record is absent")
    void processById_RecordNotFound() {
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        String result = service.processById(99);

        assertEquals("Record 99 not found", result);
    }

    // ---------------------------------------------------------------
    // 1d. findAll – bulk processing
    // ---------------------------------------------------------------

    @Test
    @DisplayName("processAll() transforms every record from the repository")
    void processAll_TransformsAllRecords() {
        when(mockRepository.findAll())
                .thenReturn(List.of("Alpha", "Beta", "Gamma"));

        List<String> results = service.processAll();

        assertEquals(List.of("Processed Alpha", "Processed Beta", "Processed Gamma"),
                results);
    }

    // ---------------------------------------------------------------
    // 1e. save – verify write path
    // ---------------------------------------------------------------

    @Test
    @DisplayName("saveData() delegates to repository.save() and returns generated ID")
    void saveData_DelegatesToRepository() {
        when(mockRepository.save("New Record")).thenReturn(42);

        int id = service.saveData("New Record");

        assertEquals(42, id);
        verify(mockRepository).save("New Record");
    }

    // ---------------------------------------------------------------
    // 1f. count – aggregate query
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getRecordCount() returns a formatted count string")
    void getRecordCount_FormatsCount() {
        when(mockRepository.count()).thenReturn(7L);

        String result = service.getRecordCount();

        assertEquals("Total records: 7", result);
    }
}
