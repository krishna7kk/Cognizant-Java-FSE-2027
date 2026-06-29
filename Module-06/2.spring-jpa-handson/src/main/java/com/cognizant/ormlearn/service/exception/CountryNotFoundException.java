package com.cognizant.ormlearn.service.exception;

/**
 * Thrown when a country lookup by code does not match any record
 * in the COUNTRY table.
 */
public class CountryNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
