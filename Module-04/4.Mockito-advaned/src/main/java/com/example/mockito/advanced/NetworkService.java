package com.example.mockito.advanced;

/**
 * Exercise 4 – Orchestrates network operations via a {@link NetworkClient}.
 */
public class NetworkService {

    private final NetworkClient networkClient;

    public NetworkService(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    /**
     * Connects to the server and returns a descriptive status string.
     * Returns "Connected to <handle>" so tests can assert on the prefix.
     */
    public String connectToServer() {
        String handle = networkClient.connect();
        return "Connected to " + handle;
    }

    /**
     * Sends a request and returns the response; wraps network errors.
     *
     * @param request payload to send
     * @return server response
     * @throws NetworkException if the client is not connected or send fails
     */
    public String sendRequest(String request) {
        if (!networkClient.isConnected()) {
            throw new NetworkException("Cannot send request: not connected");
        }
        return networkClient.sendRequest(request);
    }

    /**
     * Connects, sends one request, then disconnects cleanly.
     *
     * @param request the payload to send
     * @return the server response
     */
    public String connectSendDisconnect(String request) {
        networkClient.connect();
        String response = networkClient.sendRequest(request);
        networkClient.disconnect();
        return response;
    }
}
