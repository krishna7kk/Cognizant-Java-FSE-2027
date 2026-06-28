package com.example.mockito.advanced;

/**
 * Thrown when a network operation fails.
 */
public class NetworkException extends RuntimeException {

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
