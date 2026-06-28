package com.example.mockito.advanced;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 1 & 5 – Simulates a JPA-style database repository.
 * In production this would be backed by Hibernate / Spring Data.
 * In tests we replace it with a Mockito mock.
 */
public interface Repository {

    /** Fetches a single raw data record. */
    String getData();

    /** Fetches a record by primary key. */
    Optional<String> findById(int id);

    /** Returns all records in the table. */
    List<String> findAll();

    /** Persists a new record; returns the auto-generated ID. */
    int save(String data);

    /** Deletes a record by primary key. */
    void deleteById(int id);

    /** Returns the total number of rows. */
    long count();
}
