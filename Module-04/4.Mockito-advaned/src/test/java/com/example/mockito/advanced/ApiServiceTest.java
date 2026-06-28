package com.example.mockito.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 2: Mocking External Services (RESTful APIs)
 *
 * Key idea: real HTTP calls are slow, non-deterministic, and require
 * the target server to be running. Mocking the RestClient lets us:
 *   • Test happy paths and error responses without a live server
 *   • Simulate any HTTP status code at will
 *   • Keep tests running in milliseconds
 *
 * Pattern: inject a RestClient interface rather than a concrete
 * HttpClient class – interfaces are trivially mockable.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 2: Mocking External Services (RESTful APIs)")
class ApiServiceTest {

    @Mock
    RestClient mockRestClient;

    @InjectMocks
    ApiService apiService;

    // ---------------------------------------------------------------
    // 2a. Core scenario from the exercise brief
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchData() prefixes REST response with 'Fetched '")
    void testServiceWithMockRestClient() {
        // Arrange
        when(mockRestClient.getResponse()).thenReturn("Mock Response");

        // Act
        String result = apiService.fetchData();

        // Assert
        assertEquals("Fetched Mock Response", result);
    }

    // ---------------------------------------------------------------
    // 2b. fetchFromEndpoint – specific resource URL
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchFromEndpoint() calls get() with the correct path")
    void fetchFromEndpoint_PassesCorrectPath() {
        when(mockRestClient.get("/api/users/1")).thenReturn("User: Alice");

        String result = apiService.fetchFromEndpoint("/api/users/1");

        assertEquals("Fetched User: Alice", result);
        verify(mockRestClient).get("/api/users/1");
    }

    // ---------------------------------------------------------------
    // 2c. Simulate a 200 OK response for POST
    // ---------------------------------------------------------------

    @Test
    @DisplayName("submitData() returns true when server responds 200")
    void submitData_Returns_TrueOn200() {
        when(mockRestClient.post(anyString(), anyString())).thenReturn("Created");
        when(mockRestClient.getLastStatusCode()).thenReturn(200);

        boolean success = apiService.submitData("/api/items", "{\"name\":\"widget\"}");

        assertTrue(success, "200 OK should indicate success");
        verify(mockRestClient).post("/api/items", "{\"name\":\"widget\"}");
    }

    // ---------------------------------------------------------------
    // 2d. Simulate a 500 Server Error
    // ---------------------------------------------------------------

    @Test
    @DisplayName("submitData() returns false when server responds 500")
    void submitData_ReturnsFalseOn500() {
        when(mockRestClient.post(anyString(), anyString())).thenReturn("Error");
        when(mockRestClient.getLastStatusCode()).thenReturn(500);

        boolean success = apiService.submitData("/api/items", "{}");

        assertFalse(success, "500 Internal Server Error should indicate failure");
    }

    // ---------------------------------------------------------------
    // 2e. Simulate network failure (exception thrown by client)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchData() propagates exception when REST client throws")
    void fetchData_PropagatesRestException() {
        when(mockRestClient.getResponse())
                .thenThrow(new RuntimeException("Connection timed out"));

        assertThrows(RuntimeException.class, () -> apiService.fetchData(),
            "Service should not swallow network exceptions");
    }

    // ---------------------------------------------------------------
    // 2f. Unstubbed method returns Mockito default (null) → safe fallback
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getResponse() returning null is handled without NPE")
    void fetchData_HandlesNullResponse() {
        when(mockRestClient.getResponse()).thenReturn(null);

        // "Fetched null" – service just concatenates; no NPE
        String result = apiService.fetchData();
        assertEquals("Fetched null", result);
    }
}
