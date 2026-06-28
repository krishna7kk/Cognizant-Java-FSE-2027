package com.example;

/**
 * Exercise 4 – Exception Testing
 * Demonstrates methods that intentionally throw checked and unchecked exceptions.
 */
public class ExceptionThrower {

    /**
     * Always throws {@link IllegalArgumentException} with a descriptive message.
     *
     * @param input the value passed by the caller (used in the message)
     * @throws IllegalArgumentException unconditionally
     */
    public void throwException(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input must not be null or blank, but got: '" + input + "'");
        }
        throw new IllegalArgumentException("Invalid input value: " + input);
    }

    /**
     * Throws {@link ArithmeticException} when the divisor is zero.
     *
     * @param numerator   the top number
     * @param denominator the bottom number
     * @return the integer result of the division
     * @throws ArithmeticException if {@code denominator} is 0
     */
    public int divide(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero is undefined");
        }
        return numerator / denominator;
    }

    /**
     * Throws {@link UnsupportedOperationException} – useful for verifying
     * that a feature is intentionally unimplemented.
     *
     * @throws UnsupportedOperationException always
     */
    public void notImplemented() {
        throw new UnsupportedOperationException("This feature is not yet implemented");
    }
}
