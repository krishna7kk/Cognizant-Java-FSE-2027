package com.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 3: Argument Matching
 *
 * Core concepts:
 *   ArgumentMatchers (imported via static import):
 *     any()            – matches any object (including null)
 *     anyString()      – any non-null String
 *     anyInt()         – any int primitive / Integer
 *     eq(value)        – exactly this value (use inside matcher expressions)
 *     startsWith(s)    – String starting with s
 *     contains(s)      – String containing s
 *     argThat(lambda)  – custom predicate
 *
 *   ArgumentCaptor     – captures the actual argument passed to a mock so
 *                        you can make assertions on it afterwards.
 *
 * Rule: if you use ANY matcher in a verify/when call, ALL arguments in that
 * call must be matchers (you cannot mix literals and matchers).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 3: Argument Matching")
class Ex3_ArgumentMatchingTest {

    @Mock
    ExternalApi mockApi;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Captor
    ArgumentCaptor<Integer> intCaptor;

    // ---------------------------------------------------------------
    // 3a. Stub with eq() – exact value matcher
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getDataById(42) returns stubbed value for exact ID")
    void stubbingWithExactMatcher() {
        // Arrange – only stub for ID = 42
        when(mockApi.getDataById(eq(42))).thenReturn("Data for 42");
        MyService service = new MyService(mockApi);

        // Act
        String result = service.fetchDataById(42);

        // Assert
        assertEquals("Data for 42", result);
    }

    // ---------------------------------------------------------------
    // 3b. Stub with anyInt() – accept any integer
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Stub with anyInt() matches any ID")
    void stubbingWithAnyIntMatcher() {
        when(mockApi.getDataById(anyInt())).thenReturn("Generic Data");
        MyService service = new MyService(mockApi);

        assertEquals("Generic Data", service.fetchDataById(1));
        assertEquals("Generic Data", service.fetchDataById(999));
    }

    // ---------------------------------------------------------------
    // 3c. verify with exact argument
    // ---------------------------------------------------------------

    @Test
    @DisplayName("sendMessage() calls sendData() with 'MSG:' prefix")
    void verifyExactArgument() {
        MyService service = new MyService(mockApi);

        service.sendMessage("hello");

        // Verify the mock was called with this exact string
        verify(mockApi).sendData("MSG:hello");
    }

    // ---------------------------------------------------------------
    // 3d. verify with startsWith matcher
    // ---------------------------------------------------------------

    @Test
    @DisplayName("sendMessage() passes a payload that starts with 'MSG:'")
    void verifyWithStartsMatcher() {
        MyService service = new MyService(mockApi);

        service.sendMessage("world");

        verify(mockApi).sendData(startsWith("MSG:"));
    }

    // ---------------------------------------------------------------
    // 3e. ArgumentCaptor – capture and inspect the actual value
    // ---------------------------------------------------------------

    @Test
    @DisplayName("ArgumentCaptor captures the exact payload sent to the API")
    void captureArgument() {
        MyService service = new MyService(mockApi);

        service.sendMessage("captured-content");

        // Capture the argument that was passed to sendData()
        verify(mockApi).sendData(stringCaptor.capture());

        String capturedPayload = stringCaptor.getValue();
        assertTrue(capturedPayload.startsWith("MSG:"),
            "Payload must start with MSG:");
        assertTrue(capturedPayload.contains("captured-content"),
            "Payload must contain the original message");
        assertEquals("MSG:captured-content", capturedPayload);
    }

    // ---------------------------------------------------------------
    // 3f. argThat() – custom predicate matcher
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getDataById is called with a positive ID")
    void verifyWithCustomMatcher() {
        MyService service = new MyService(mockApi);

        service.fetchDataById(7);

        // Custom matcher: argument must be a positive integer
        verify(mockApi).getDataById(argThat(id -> id > 0));
    }
}
