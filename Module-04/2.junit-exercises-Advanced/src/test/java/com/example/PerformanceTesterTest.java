package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercise 5: Timeout and Performance Testing
 *
 * Two JUnit 5 mechanisms:
 *
 *   1. assertTimeout(Duration, Executable)
 *      – Runs the executable in the SAME thread.
 *      – Waits for it to complete, then fails if the duration was exceeded.
 *      – Use when you still want the full stack trace on failure.
 *
 *   2. assertTimeoutPreemptively(Duration, Executable)
 *      – Runs the executable in a SEPARATE thread.
 *      – Aborts the thread immediately when the timeout elapses.
 *      – Use when you cannot let a slow test block the entire suite.
 *
 *   3. @Timeout(value, unit)  (class- or method-level annotation)
 *      – Declarative alternative; fails the test if its total execution
 *        exceeds the given duration (preemptive by default from JUnit 5.5+).
 */
@DisplayName("Exercise 5: Timeout and Performance Testing")
class PerformanceTesterTest {

    private final PerformanceTester tester = new PerformanceTester();

    // ------------------------------------------------------------------
    // 5a. assertTimeout – cooperative timeout (same thread)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Fast task completes within 500 ms (assertTimeout)")
    void fastTaskCompletesWithinLimit() {
        // Arrange
        Duration limit = Duration.ofMillis(500);

        // Act + Assert – lambda must finish before `limit` elapses
        String result = assertTimeout(limit,
            () -> tester.performFastTask(),
            "performFastTask() should finish well within 500 ms"
        );

        assertEquals("fast-result", result, "Return value should be 'fast-result'");
    }

    @Test
    @DisplayName("Configurable task (100 ms) passes a 500 ms limit (assertTimeout)")
    void configurableTaskPassesLimit() {
        assertTimeout(Duration.ofMillis(500),
            () -> tester.performTask(100));
    }

    // ------------------------------------------------------------------
    // 5b. assertTimeoutPreemptively – abort on timeout (separate thread)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Fast task completes within 500 ms (assertTimeoutPreemptively)")
    void fastTaskPreemptiveTimeout() {
        assertTimeoutPreemptively(
            Duration.ofMillis(500),
            () -> tester.performFastTask(),
            "performFastTask() must not exceed 500 ms"
        );
    }

    @Test
    @DisplayName("Slow task (2 s) is expected to exceed a 500 ms preemptive limit")
    void slowTaskExceedsPreemptiveLimit() {
        // We EXPECT this assertion to throw an AssertionError because the slow
        // task takes 2 000 ms but the limit is 500 ms.
        // Wrapping in assertThrows lets the test PASS when the timeout fires.
        assertThrows(
            AssertionError.class,
            () -> assertTimeoutPreemptively(
                    Duration.ofMillis(500),
                    () -> tester.performSlowTask()
            ),
            "assertTimeoutPreemptively should throw AssertionError for a 2 s task within 500 ms"
        );
    }

    // ------------------------------------------------------------------
    // 5c. @Timeout annotation – declarative style
    // ------------------------------------------------------------------

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Fast task passes @Timeout(500 ms)")
    void fastTaskAnnotationTimeout() throws InterruptedException {
        // If this method takes more than 500 ms, JUnit will fail it automatically
        tester.performFastTask();
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @DisplayName("Configurable task (200 ms) passes @Timeout(1 s)")
    void configurableTaskAnnotationTimeout() {
        String result = tester.performTask(200);
        assertNotNull(result, "Result must not be null");
    }

    // ------------------------------------------------------------------
    // 5d. Boundary test – right at the edge of the limit
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Task finishing at exactly ~50 ms comfortably passes a 300 ms limit")
    void boundaryTaskPassesComfortably() {
        long taskDurationMs  = 50;
        Duration limit       = Duration.ofMillis(300);

        assertTimeout(limit, () -> tester.performTask(taskDurationMs),
            "A " + taskDurationMs + " ms task should pass a " + limit.toMillis() + " ms limit");
    }
}
