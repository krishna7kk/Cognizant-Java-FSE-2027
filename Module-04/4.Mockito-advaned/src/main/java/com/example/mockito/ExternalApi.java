package com.example.mockito;

/**
 * Represents an external API that would normally make HTTP calls,
 * hit a database, or do other I/O we do NOT want in unit tests.
 *
 * In tests we replace this with a Mockito mock so tests are:
 *   – Fast   (no real network / DB)
 *   – Stable (no flaky third-party services)
 *   – Focused (we control exactly what the API returns)
 */
public interface ExternalApi {

    /** Returns data fetched from a remote source. */
    String getData();

    /** Returns data for a specific resource ID. */
    String getDataById(int id);

    /** Sends a payload to the remote source. */
    void sendData(String payload);

    /** Deletes a resource; throws if the ID does not exist. */
    void deleteById(int id);

    /** Returns the count of available records. */
    int getCount();
}
