package com.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercise 1: Mocking and Stubbing
 *
 * Core concepts:
 *   mock(Class)   – creates a fake object that records calls and returns defaults
 *   when(...).thenReturn(...) – programs the mock to return a specific value
 *   @Mock         – annotation shorthand (requires @ExtendWith(MockitoExtension.class))
 *
 * Why mock?
 *   ExternalApi would normally hit a real server. Mocking lets us:
 *     • run tests offline and instantly
 *     • control exactly what the API "returns"
 *     • isolate MyService so only its logic is tested
 */
@ExtendWith(MockitoExtension.class)          // activates @Mock, @InjectMocks, etc.
@DisplayName("Exercise 1: Mocking and Stubbing")
class Ex1_MockingAndStubbingTest {

    // ---------------------------------------------------------------
    // Approach A – programmatic mock (Mockito.mock)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchData() returns stubbed value (programmatic mock)")
    void fetchDataReturnsStubbedValue_Programmatic() {
        // Arrange – create mock and stub
        ExternalApi mockApi = mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Mock Data");

        MyService service = new MyService(mockApi);

        // Act
        String result = service.fetchData();

        // Assert
        assertEquals("Mock Data", result,
            "fetchData() should return whatever the stubbed API returns");
    }

    // ---------------------------------------------------------------
    // Approach B – annotation-based mock (@Mock)
    // ---------------------------------------------------------------

    @Mock
    ExternalApi annotatedApi;               // Mockito injects this automatically

    @Test
    @DisplayName("fetchData() returns stubbed value (@Mock annotation)")
    void fetchDataReturnsStubbedValue_Annotation() {
        // Arrange
        when(annotatedApi.getData()).thenReturn("Annotation Mock Data");
        MyService service = new MyService(annotatedApi);

        // Act
        String result = service.fetchData();

        // Assert
        assertEquals("Annotation Mock Data", result);
    }

    // ---------------------------------------------------------------
    // Approach C – stub with different data
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Stub can return any predefined value we choose")
    void stubCanReturnAnyValue() {
        // Arrange
        ExternalApi mockApi = mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Custom Response 42");

        MyService service = new MyService(mockApi);

        // Act
        String result = service.fetchData();

        // Assert
        assertEquals("Custom Response 42", result);
    }

    // ---------------------------------------------------------------
    // Approach D – unstubbed method returns Mockito default (null / 0)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Unstubbed method returns Mockito default (null for Object)")
    void unstubbedMethodReturnsDefault() {
        ExternalApi mockApi = mock(ExternalApi.class);
        // getData() is NOT stubbed → Mockito returns null by default
        MyService service = new MyService(mockApi);

        String result = service.fetchData();

        assertEquals(null, result, "Unstubbed String method returns null by default");
    }
}
