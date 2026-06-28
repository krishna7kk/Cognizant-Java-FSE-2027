package com.example.mockito.advanced;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Exercise 1 & 5 – Business-logic layer that depends on {@link Repository}.
 * Constructor injection makes it easy to swap a real repo for a mock.
 */
public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    /**
     * Fetches raw data from the repository and applies a simple transformation.
     * Returns "Processed <data>" so tests can assert on the prefix.
     */
    public String processData() {
        String raw = repository.getData();
        return "Processed " + raw;
    }

    /**
     * Looks up a record by ID; returns a default message when not found.
     */
    public String processById(int id) {
        return repository.findById(id)
                .map(data -> "Processed " + data)
                .orElse("Record " + id + " not found");
    }

    /**
     * Processes all records in bulk.
     */
    public List<String> processAll() {
        return repository.findAll().stream()
                .map(data -> "Processed " + data)
                .collect(Collectors.toList());
    }

    /**
     * Persists new data and returns the generated ID.
     */
    public int saveData(String data) {
        return repository.save(data);
    }

    /**
     * Returns a human-readable count string.
     */
    public String getRecordCount() {
        return "Total records: " + repository.count();
    }
}
