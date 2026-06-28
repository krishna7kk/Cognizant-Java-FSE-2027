package com.example;

/**
 * Exercise 5 – Timeout and Performance Testing
 * Contains methods with deliberately varying execution times so that
 * JUnit's timeout assertions can be meaningfully demonstrated.
 */
public class PerformanceTester {

    /**
     * Simulates a fast task that completes well within any reasonable limit.
     * Actual sleep: ~50 ms.
     */
    public String performFastTask() {
        sleep(50);
        return "fast-result";
    }

    /**
     * Simulates a task that intentionally takes too long (2 000 ms).
     * Tests annotated with a short timeout should fail when calling this.
     */
    public String performSlowTask() {
        sleep(2_000);
        return "slow-result";
    }

    /**
     * Performs a configurable-duration task useful for boundary testing.
     *
     * @param durationMs how long to sleep in milliseconds
     * @return a result string
     */
    public String performTask(long durationMs) {
        sleep(durationMs);
        return "result-after-" + durationMs + "ms";
    }

    // ---------------------------------------------------------------
    // Helper
    // ---------------------------------------------------------------

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
