package com.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 5: Mocking and Stubbing with Multiple Returns
 *
 * Core concepts:
 *   when(mock.method())
 *       .thenReturn(first)
 *       .thenReturn(second)
 *       .thenReturn(third)
 *
 *   – Shorthand: .thenReturn(first, second, third)
 *   – After all supplied values are exhausted, the LAST value repeats.
 *
 *   thenThrow() can be mixed in too:
 *       .thenReturn("ok").thenThrow(new RuntimeException("boom"))
 *
 * Use-case: simulate pagination, retry logic, or state changes across calls.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 5: Multiple Return Values")
class Ex5_MultipleReturnsTest {

    @Mock
    ExternalApi mockApi;

    // ---------------------------------------------------------------
    // 5a. Chained thenReturn
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getData() returns different values on consecutive calls (chained)")
    void chainedThenReturn() {
        // Arrange – 1st call → "First", 2nd → "Second", 3rd → "Third"
        when(mockApi.getData())
                .thenReturn("First")
                .thenReturn("Second")
                .thenReturn("Third");

        MyService service = new MyService(mockApi);

        // Act
        List<String> results = service.fetchMultiple(3);

        // Assert
        assertEquals(List.of("First", "Second", "Third"), results);
    }

    // ---------------------------------------------------------------
    // 5b. Vararg shorthand – same result, cleaner syntax
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getData() returns different values on consecutive calls (vararg)")
    void varargThenReturn() {
        when(mockApi.getData()).thenReturn("A", "B", "C");
        MyService service = new MyService(mockApi);

        List<String> results = service.fetchMultiple(3);

        assertEquals(List.of("A", "B", "C"), results);
    }

    // ---------------------------------------------------------------
    // 5c. Last value repeats after all stubs are consumed
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Last stubbed value repeats when extra calls are made")
    void lastValueRepeats() {
        when(mockApi.getData()).thenReturn("X", "Y");   // only 2 stubs
        MyService service = new MyService(mockApi);

        List<String> results = service.fetchMultiple(4); // 4 calls

        // Call 1 → X, Call 2 → Y, Call 3 → Y (repeat), Call 4 → Y (repeat)
        assertEquals("X", results.get(0));
        assertEquals("Y", results.get(1));
        assertEquals("Y", results.get(2), "3rd call should repeat last stubbed value");
        assertEquals("Y", results.get(3), "4th call should repeat last stubbed value");
    }

    // ---------------------------------------------------------------
    // 5d. Mix thenReturn and thenThrow
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Stub can return a value then throw on subsequent call")
    void thenReturnFollowedByThenThrow() {
        when(mockApi.getData())
                .thenReturn("Success")
                .thenThrow(new RuntimeException("API unavailable"));

        MyService service = new MyService(mockApi);

        // First call succeeds
        String first = service.fetchData();
        assertEquals("Success", first);

        // Second call throws
        assertThrows(RuntimeException.class, () -> service.fetchData());
    }

    // ---------------------------------------------------------------
    // 5e. Different stubs for different argument values
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Different return values for different arguments")
    void differentReturnsForDifferentArgs() {
        when(mockApi.getDataById(1)).thenReturn("Record One");
        when(mockApi.getDataById(2)).thenReturn("Record Two");
        when(mockApi.getDataById(99)).thenReturn("Record Ninety-Nine");

        MyService service = new MyService(mockApi);

        assertEquals("Record One",         service.fetchDataById(1));
        assertEquals("Record Two",         service.fetchDataById(2));
        assertEquals("Record Ninety-Nine", service.fetchDataById(99));
        assertNull(service.fetchDataById(999)); // unstubbed → null
    }
}
