package com.example.mockito.advanced;

import java.util.Map;

/**
 * Exercise 2 – Represents an HTTP/REST client.
 * In production this would wrap an HttpClient, OkHttp, or RestTemplate.
 * In tests we mock it so no real network call is made.
 */
public interface RestClient {

    /** Issues a GET request and returns the response body as a String. */
    String getResponse();

    /** Issues a GET request to a specific endpoint. */
    String get(String endpoint);

    /** Issues a POST request with a JSON body; returns the response body. */
    String post(String endpoint, String body);

    /** Returns the HTTP status code of the last request. */
    int getLastStatusCode();
}
