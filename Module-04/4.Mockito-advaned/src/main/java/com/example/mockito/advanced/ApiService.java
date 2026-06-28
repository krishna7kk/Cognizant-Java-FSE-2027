package com.example.mockito.advanced;

/**
 * Exercise 2 – Orchestrates calls to an external RESTful API.
 */
public class ApiService {

    private final RestClient restClient;

    public ApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Fetches data from the default endpoint and decorates the response.
     * Returns "Fetched <response>" so tests can assert on the prefix.
     */
    public String fetchData() {
        String response = restClient.getResponse();
        return "Fetched " + response;
    }

    /**
     * Fetches data from a specific resource endpoint.
     */
    public String fetchFromEndpoint(String endpoint) {
        String response = restClient.get(endpoint);
        return "Fetched " + response;
    }

    /**
     * Posts a payload and returns whether the operation succeeded (2xx).
     */
    public boolean submitData(String endpoint, String payload) {
        restClient.post(endpoint, payload);
        int status = restClient.getLastStatusCode();
        return status >= 200 && status < 300;
    }
}
