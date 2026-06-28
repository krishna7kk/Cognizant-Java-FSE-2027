package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercise 1: Parameterized Tests
 *
 * Key annotations:
 *   @ParameterizedTest  – marks a test method that runs multiple times,
 *                         once per supplied argument.
 *   @ValueSource        – supplies a simple array of literals (ints, strings, …).
 *   @CsvSource          – supplies comma-separated rows; each row becomes one
 *                         invocation with multiple arguments.
 *
 * Benefit: one test method replaces N identical copy-pasted test methods.
 */
class EvenCheckerTest {

    private final EvenChecker checker = new EvenChecker();

    // ------------------------------------------------------------------
    // 1a. @ValueSource – test a list of numbers that ARE even
    // ------------------------------------------------------------------

    @ParameterizedTest(name = "{0} should be even")
    @ValueSource(ints = {0, 2, 4, 6, 100, -8, Integer.MAX_VALUE - 1})
    void evenNumbersReturnTrue(int number) {
        // Arrange: number is provided by @ValueSource
        // Act
        boolean result = checker.isEven(number);
        // Assert
        assertTrue(result, number + " should be recognised as even");
    }

    // ------------------------------------------------------------------
    // 1b. @ValueSource – test a list of numbers that are NOT even (odd)
    // ------------------------------------------------------------------

    @ParameterizedTest(name = "{0} should be odd")
    @ValueSource(ints = {1, 3, 5, 7, 99, -3, Integer.MAX_VALUE})
    void oddNumbersReturnFalse(int number) {
        boolean result = checker.isEven(number);
        assertFalse(result, number + " should NOT be recognised as even");
    }

    // ------------------------------------------------------------------
    // 1c. @CsvSource – supply both the input and the expected result
    //     in a single data-driven test, making intent crystal-clear.
    // ------------------------------------------------------------------

    @ParameterizedTest(name = "isEven({0}) == {1}")
    @CsvSource({
        "0,   true",
        "1,   false",
        "2,   true",
        "3,   false",
        "-4,  true",
        "-7,  false",
        "100, true",
        "101, false"
    })
    void isEvenMatchesExpected(int number, boolean expected) {
        assertEquals(expected, checker.isEven(number),
            "isEven(" + number + ") should return " + expected);
    }
}
