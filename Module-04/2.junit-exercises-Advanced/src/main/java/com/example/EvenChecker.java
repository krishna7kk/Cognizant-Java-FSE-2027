package com.example;

/**
 * Exercise 1 – Parameterized Tests
 * Simple utility that decides whether an integer is even.
 */
public class EvenChecker {

    /**
     * Returns {@code true} when {@code number} is evenly divisible by 2.
     *
     * @param number the integer to check
     * @return {@code true} if even, {@code false} if odd
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}
