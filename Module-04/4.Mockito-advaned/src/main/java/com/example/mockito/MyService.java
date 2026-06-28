package com.example.mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Business-logic service that depends on {@link ExternalApi}.
 *
 * We inject the dependency through the constructor so Mockito (or any
 * other mechanism) can substitute a mock during testing.
 */
public class MyService {

    private final ExternalApi api;

    public MyService(ExternalApi api) {
        this.api = api;
    }

    // ------------------------------------------------------------------
    // Methods used across the seven exercises
    // ------------------------------------------------------------------

    /**
     * Exercise 1 & 2 – delegates directly to the API.
     *
     * @return whatever the API returns
     */
    public String fetchData() {
        return api.getData();
    }

    /**
     * Exercise 3 – fetches data for a specific ID (argument matching demo).
     *
     * @param id resource identifier
     * @return data string for the given ID
     */
    public String fetchDataById(int id) {
        return api.getDataById(id);
    }

    /**
     * Exercise 4 – sends a formatted payload (void method demo).
     *
     * @param message raw message to transmit
     */
    public void sendMessage(String message) {
        String payload = "MSG:" + message;
        api.sendData(payload);
    }

    /**
     * Exercise 5 – calls getData() multiple times and collects results.
     * Demonstrates testing when a stub returns different values on consecutive calls.
     *
     * @param times how many times to fetch
     * @return list of results in call order
     */
    public List<String> fetchMultiple(int times) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            results.add(api.getData());
        }
        return results;
    }

    /**
     * Exercise 6 – performs an ordered sequence: fetch, then send.
     * Useful for verifying that operations happen in a specific order.
     *
     * @param payload the data to send after fetching
     * @return the fetched data (side-effect: also sends payload)
     */
    public String fetchThenSend(String payload) {
        String fetched = api.getData();   // must happen FIRST
        api.sendData(payload);             // must happen SECOND
        return fetched;
    }

    /**
     * Exercise 7 – attempts to delete a resource; propagates any exception.
     *
     * @param id resource to delete
     * @throws RuntimeException re-thrown if the API signals an error
     */
    public void deleteResource(int id) {
        try {
            api.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete resource " + id, e);
        }
    }
}
