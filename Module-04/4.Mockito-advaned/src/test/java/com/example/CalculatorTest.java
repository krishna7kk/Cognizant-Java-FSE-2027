package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Exercise 2: Writing Basic JUnit Tests
 * Tests for the Calculator class covering all four operations.
 */
public class CalculatorTest {

    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        int result = calc.add(3, 4);
        assertEquals(7, result);
    }

    @Test
    public void testSubtract() {
        Calculator calc = new Calculator();
        int result = calc.subtract(10, 4);
        assertEquals(6, result);
    }

    @Test
    public void testMultiply() {
        Calculator calc = new Calculator();
        int result = calc.multiply(3, 5);
        assertEquals(15, result);
    }

    @Test
    public void testDivide() {
        Calculator calc = new Calculator();
        double result = calc.divide(10, 2);
        assertEquals(5.0, result, 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZeroThrowsException() {
        Calculator calc = new Calculator();
        calc.divide(10, 0); // should throw ArithmeticException
    }
}
