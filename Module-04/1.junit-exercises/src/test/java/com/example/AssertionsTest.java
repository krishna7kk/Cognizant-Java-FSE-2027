package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Exercise 3: Assertions in JUnit
 * Demonstrates the most commonly used JUnit 4 assertion methods.
 */
public class AssertionsTest {

    @Test
    public void testAssertEquals() {
        // Checks that two values are equal
        assertEquals("2 + 3 should equal 5", 5, 2 + 3);
        assertEquals("String comparison", "hello", "hel" + "lo");
        assertEquals("Double comparison with delta", 3.14, Math.PI, 0.01);
    }

    @Test
    public void testAssertTrue() {
        // Checks that a condition is true
        assertTrue("5 should be greater than 3", 5 > 3);
        assertTrue("List should be empty",  new java.util.ArrayList<>().isEmpty());
    }

    @Test
    public void testAssertFalse() {
        // Checks that a condition is false
        assertFalse("5 should not be less than 3", 5 < 3);
        assertFalse("Non-empty string is not blank", "hello".isEmpty());
    }

    @Test
    public void testAssertNull() {
        // Checks that an object is null
        String s = null;
        assertNull("Variable should be null", s);
    }

    @Test
    public void testAssertNotNull() {
        // Checks that an object is NOT null
        Object obj = new Object();
        assertNotNull("Object should not be null", obj);
        assertNotNull("New string should not be null", new String("test"));
    }

    @Test
    public void testAssertSame() {
        // Checks that two references point to the same object
        String a = "shared";
        String b = a;
        assertSame("Both variables should reference the same object", a, b);
    }

    @Test
    public void testAssertNotSame() {
        // Checks that two references do NOT point to the same object
        String a = new String("hello");
        String b = new String("hello");
        assertNotSame("Different instances should not be the same reference", a, b);
    }

    @Test
    public void testAssertArrayEquals() {
        // Checks that two arrays are equal element by element
        int[] expected = {1, 2, 3};
        int[] actual   = {1, 2, 3};
        assertArrayEquals("Arrays should be equal", expected, actual);
    }

    @Test
    public void testAllAssertionsTogether() {
        assertEquals(5, 2 + 3);
        assertTrue(5 > 3);
        assertFalse(5 < 3);
        assertNull(null);
        assertNotNull(new Object());
    }
}
