package com.example.mockito.advanced;

/**
 * Exercise 4 – Abstraction over a low-level network connection.
 * Mocking this avoids opening real sockets during unit tests.
 */
public interface NetworkClient {

    /**
     * Opens a connection to the configured host and returns a connection handle.
     *
     * @return connection identifier / description
     * @throws NetworkException if the connection cannot be established
     */
    String connect();

    /**
     * Sends a request over the established connection.
     *
     * @param request the request payload
     * @return the server's response
     */
    String sendRequest(String request);

    /**
     * Closes the current connection.
     */
    void disconnect();

    /**
     * Returns {@code true} if a connection is currently open.
     */
    boolean isConnected();
}
