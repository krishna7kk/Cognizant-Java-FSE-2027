package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercise 4: Exception Testing
 *
 * JUnit 5 approach – assertThrows():
 *   Throwable ex = assertThrows(ExpectedType.class, () -> codeUnderTest());
 *
 *   • The lambda MUST throw the specified exception, otherwise the test fails.
 *   • assertThrows() returns the exception so you can make additional
 *     assertions on its message, cause, etc.
 *
 * JUnit 4 approach (kept in CalculatorTest) used @Test(expected = Foo.class).
 * The JUnit 5 approach is preferred because:
 *   – you can assert on the exception's message
 *   – only the specific line in the lambda is expected to throw (not the
 *     entire test method), preventing false positives.
 */
@DisplayName("Exercise 4: Exception Testing")
class ExceptionThrowerTest {

    private final ExceptionThrower thrower = new ExceptionThrower();

    // ------------------------------------------------------------------
    // 4a. Basic assertThrows – verify the type
    // ------------------------------------------------------------------

    @Test
    @DisplayName("throwException() must throw IllegalArgumentException")
    void throwExceptionThrowsIllegalArgumentException() {
        // Act + Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> thrower.throwException("bad-value"),
            "throwException() should throw IllegalArgumentException"
        );
    }

    // ------------------------------------------------------------------
    // 4b. Inspect the exception message after catching it
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Exception message should contain the bad input value")
    void exceptionMessageContainsInputValue() {
        // Arrange
        String badInput = "bad-value";

        // Act – assertThrows returns the caught exception
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> thrower.throwException(badInput)
        );

        // Assert on the message
        assertTrue(
            ex.getMessage().contains(badInput),
            "Message should contain the rejected input: " + ex.getMessage()
        );
    }

    // ------------------------------------------------------------------
    // 4c. Null / blank input
    // ------------------------------------------------------------------

    @Test
    @DisplayName("throwException(null) must throw IllegalArgumentException")
    void throwExceptionWithNullInput() {
        assertThrows(
            IllegalArgumentException.class,
            () -> thrower.throwException(null)
        );
    }

    @Test
    @DisplayName("throwException(blank) must throw IllegalArgumentException")
    void throwExceptionWithBlankInput() {
        assertThrows(
            IllegalArgumentException.class,
            () -> thrower.throwException("   ")
        );
    }

    // ------------------------------------------------------------------
    // 4d. divide() – verify ArithmeticException on zero denominator
    // ------------------------------------------------------------------

    @Test
    @DisplayName("divide() throws ArithmeticException when denominator is 0")
    void divideByZeroThrowsArithmeticException() {
        ArithmeticException ex = assertThrows(
            ArithmeticException.class,
            () -> thrower.divide(10, 0)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("zero"),
            "Message should mention 'zero'");
    }

    @Test
    @DisplayName("divide() succeeds for normal inputs (no exception)")
    void divideNormalInputsDoesNotThrow() {
        // assertDoesNotThrow verifies no exception is thrown
        int result = assertDoesNotThrow(
            () -> thrower.divide(10, 2),
            "divide(10, 2) should NOT throw any exception"
        );
        assertEquals(5, result);
    }

    // ------------------------------------------------------------------
    // 4e. UnsupportedOperationException
    // ------------------------------------------------------------------

    @Test
    @DisplayName("notImplemented() throws UnsupportedOperationException")
    void notImplementedThrowsUnsupportedOperationException() {
        assertThrows(
            UnsupportedOperationException.class,
            () -> thrower.notImplemented()
        );
    }
}
