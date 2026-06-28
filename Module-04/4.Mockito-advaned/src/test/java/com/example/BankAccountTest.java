package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Exercise 4: AAA Pattern + Test Fixtures + Setup and Teardown
 *
 * @Before  – runs before EACH test method (setup / arrange shared state)
 * @After   – runs after  EACH test method (teardown / cleanup)
 *
 * AAA Pattern:
 *   Arrange – set up the objects and data needed for the test
 *   Act     – call the method under test
 *   Assert  – verify the result
 */
public class BankAccountTest {

    // Shared fixture: created fresh before every test
    private BankAccount account;

    // ---------------------------------------------------------------
    // Setup & Teardown
    // ---------------------------------------------------------------

    @Before
    public void setUp() {
        // Runs before EACH test – guarantees a clean starting state
        account = new BankAccount("Alice", 1000.0);
        System.out.println("setUp() – fresh BankAccount created with balance 1000.0");
    }

    @After
    public void tearDown() {
        // Runs after EACH test – release resources / log completion
        account = null;
        System.out.println("tearDown() – BankAccount reference cleared");
    }

    // ---------------------------------------------------------------
    // Tests using the AAA pattern
    // ---------------------------------------------------------------

    @Test
    public void testDeposit() {
        // Arrange
        double depositAmount = 500.0;

        // Act
        account.deposit(depositAmount);

        // Assert
        assertEquals("Balance after deposit should be 1500.0",
                1500.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw() {
        // Arrange
        double withdrawAmount = 200.0;

        // Act
        account.withdraw(withdrawAmount);

        // Assert
        assertEquals("Balance after withdrawal should be 800.0",
                800.0, account.getBalance(), 0.001);
    }

    @Test
    public void testMultipleTransactions() {
        // Arrange
        double deposit    = 300.0;
        double withdrawal = 150.0;

        // Act
        account.deposit(deposit);
        account.withdraw(withdrawal);

        // Assert
        assertEquals("Balance after deposit then withdrawal should be 1150.0",
                1150.0, account.getBalance(), 0.001);
    }

    @Test(expected = IllegalStateException.class)
    public void testOverdraftThrowsException() {
        // Arrange
        double overdraftAmount = 9999.0; // more than the 1000 balance

        // Act & Assert (exception expected)
        account.withdraw(overdraftAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDepositThrowsException() {
        // Arrange
        double negativeAmount = -50.0;

        // Act & Assert (exception expected)
        account.deposit(negativeAmount);
    }

    @Test
    public void testInitialOwner() {
        // Arrange – already done in setUp()

        // Act
        String owner = account.getOwner();

        // Assert
        assertEquals("Owner should be Alice", "Alice", owner);
    }

    @Test
    public void testInitialBalance() {
        // Arrange – already done in setUp()

        // Act
        double balance = account.getBalance();

        // Assert
        assertEquals("Initial balance should be 1000.0", 1000.0, balance, 0.001);
    }
}
