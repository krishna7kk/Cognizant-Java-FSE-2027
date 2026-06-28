package com.example.mockito.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 4: Mocking Network Interactions
 *
 * Key idea: opening real sockets requires available ports, firewall rules,
 * and a live server. Mocking NetworkClient eliminates all of those,
 * letting us test connection handling, request/response logic, and
 * failure scenarios purely in-process.
 *
 * This exercise also demonstrates:
 *   • Stubbing boolean methods (isConnected)
 *   • Verifying ordered connect → send → disconnect sequences (InOrder)
 *   • Simulating connection failures (doThrow on connect())
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 4: Mocking Network Interactions")
class NetworkServiceTest {

    @Mock
    NetworkClient mockNetworkClient;

    @InjectMocks
    NetworkService networkService;

    // ---------------------------------------------------------------
    // 4a. Core scenario from the exercise brief
    // ---------------------------------------------------------------

    @Test
    @DisplayName("connectToServer() prefixes the connection handle with 'Connected to '")
    void testServiceWithMockNetworkClient() {
        // Arrange
        when(mockNetworkClient.connect()).thenReturn("Mock Connection");

        // Act
        String result = networkService.connectToServer();

        // Assert
        assertEquals("Connected to Mock Connection", result);
        verify(mockNetworkClient).connect();
    }

    // ---------------------------------------------------------------
    // 4b. sendRequest – happy path (already connected)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("sendRequest() returns server response when client is connected")
    void sendRequest_WhenConnected_ReturnsResponse() {
        when(mockNetworkClient.isConnected()).thenReturn(true);
        when(mockNetworkClient.sendRequest("PING")).thenReturn("PONG");

        String response = networkService.sendRequest("PING");

        assertEquals("PONG", response);
        verify(mockNetworkClient).sendRequest("PING");
    }

    // ---------------------------------------------------------------
    // 4c. sendRequest – not connected → NetworkException
    // ---------------------------------------------------------------

    @Test
    @DisplayName("sendRequest() throws NetworkException when client is not connected")
    void sendRequest_WhenNotConnected_ThrowsException() {
        when(mockNetworkClient.isConnected()).thenReturn(false);

        NetworkException ex = assertThrows(
            NetworkException.class,
            () -> networkService.sendRequest("PING")
        );
        assertTrue(ex.getMessage().contains("not connected"));
        verify(mockNetworkClient, never()).sendRequest(anyString());
    }

    // ---------------------------------------------------------------
    // 4d. connectSendDisconnect – ordered sequence
    // ---------------------------------------------------------------

    @Test
    @DisplayName("connectSendDisconnect() follows connect → send → disconnect order")
    void connectSendDisconnect_FollowsProtocolOrder() {
        when(mockNetworkClient.connect()).thenReturn("ServerA");
        when(mockNetworkClient.sendRequest("HELLO")).thenReturn("WORLD");

        String response = networkService.connectSendDisconnect("HELLO");

        assertEquals("WORLD", response);

        InOrder inOrder = inOrder(mockNetworkClient);
        inOrder.verify(mockNetworkClient).connect();
        inOrder.verify(mockNetworkClient).sendRequest("HELLO");
        inOrder.verify(mockNetworkClient).disconnect();
    }

    // ---------------------------------------------------------------
    // 4e. Connection failure – connect() throws
    // ---------------------------------------------------------------

    @Test
    @DisplayName("connectToServer() propagates NetworkException on connection failure")
    void connectToServer_PropagatesConnectionFailure() {
        when(mockNetworkClient.connect())
                .thenThrow(new NetworkException("Host unreachable"));

        assertThrows(NetworkException.class, () -> networkService.connectToServer());
    }

    // ---------------------------------------------------------------
    // 4f. disconnect() is void – verify it is called on clean shutdown
    // ---------------------------------------------------------------

    @Test
    @DisplayName("connectSendDisconnect() always calls disconnect() after sending")
    void connectSendDisconnect_AlwaysDisconnects() {
        when(mockNetworkClient.connect()).thenReturn("handle");
        when(mockNetworkClient.sendRequest(anyString())).thenReturn("ok");

        networkService.connectSendDisconnect("DATA");

        verify(mockNetworkClient).disconnect();
    }
}
