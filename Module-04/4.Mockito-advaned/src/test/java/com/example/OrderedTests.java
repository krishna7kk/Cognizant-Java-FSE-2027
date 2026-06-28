package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercise 3: Test Execution Order
 *
 * By default JUnit 5 does NOT guarantee method execution order.
 * Use these annotations to take explicit control:
 *
 *   @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 *       – activates order-based sorting for this class.
 *
 *   @Order(n)
 *       – assigns a numeric priority; lower numbers run first.
 *       – methods without @Order run last in undefined order.
 *
 * Other built-in MethodOrderers:
 *   MethodOrderer.DisplayName   – alphabetical by display name
 *   MethodOrderer.MethodName    – alphabetical by method name
 *   MethodOrderer.Random        – random (useful for spotting order-dependencies)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Exercise 3: Ordered Test Execution")
class OrderedTests {

    // Shared mutable state to prove tests actually execute in order
    private static final java.util.List<String> executionLog = new java.util.ArrayList<>();

    // ---------------------------------------------------------------
    // Lifecycle hooks
    // ---------------------------------------------------------------

    @BeforeEach
    void logStart(TestInfo info) {
        System.out.println("▶ Starting: " + info.getDisplayName());
    }

    @AfterEach
    void logEnd(TestInfo info) {
        System.out.println("✔ Finished: " + info.getDisplayName());
    }

    @AfterAll
    static void printLog() {
        System.out.println("\nExecution order recorded: " + executionLog);
    }

    // ---------------------------------------------------------------
    // Ordered tests
    // ---------------------------------------------------------------

    @Test
    @Order(1)
    @DisplayName("Step 1 – Initialize the system")
    void initializeSystem() {
        executionLog.add("init");
        System.out.println("  [1] Initializing system…");
        assertTrue(executionLog.contains("init"), "init should be logged first");
    }

    @Test
    @Order(2)
    @DisplayName("Step 2 – Load configuration")
    void loadConfiguration() {
        executionLog.add("config");
        System.out.println("  [2] Loading configuration…");
        // 'init' must have run before 'config'
        assertEquals("init", executionLog.get(0), "init must precede config");
        assertTrue(executionLog.contains("config"));
    }

    @Test
    @Order(3)
    @DisplayName("Step 3 – Connect to database")
    void connectToDatabase() {
        executionLog.add("db-connect");
        System.out.println("  [3] Connecting to database…");
        assertEquals(2, executionLog.size() - 1,
            "db-connect should be the third entry");
    }

    @Test
    @Order(4)
    @DisplayName("Step 4 – Execute business logic")
    void executeBusinessLogic() {
        executionLog.add("business-logic");
        System.out.println("  [4] Executing business logic…");
        assertTrue(executionLog.contains("db-connect"),
            "Database must be connected before business logic runs");
    }

    @Test
    @Order(5)
    @DisplayName("Step 5 – Verify and shut down")
    void verifyAndShutdown() {
        executionLog.add("shutdown");
        System.out.println("  [5] Shutting down…");

        // All previous steps must have run in the correct sequence
        assertEquals(java.util.List.of("init", "config", "db-connect", "business-logic", "shutdown"),
            executionLog,
            "Full execution sequence must match expected order");
    }

    // ---------------------------------------------------------------
    // Demonstrating DisplayName ordering (separate logical group)
    // ---------------------------------------------------------------

    @Test
    @Order(10)
    @DisplayName("Bonus – Arithmetic sanity check")
    void arithmeticSanityCheck() {
        assertEquals(42, 6 * 7, "6 × 7 should equal 42");
    }
}
