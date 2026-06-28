package com.example;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Exercise 2: Test Suites and Categories
 *
 * @Suite           – marks this class as a JUnit Platform suite runner.
 *                   The class itself contains no test methods; its sole
 *                   purpose is to aggregate other test classes.
 *
 * @SelectClasses   – lists every test class that should be included in
 *                   this suite.  Running AllTests via Maven or the IDE
 *                   executes all of those classes together.
 *
 * @SuiteDisplayName – human-readable label shown in IDE / reports.
 *
 * How to run:
 *   mvn test -Dtest=AllTests
 *   – or –
 *   Right-click AllTests in the IDE and choose "Run".
 */
@Suite
@SuiteDisplayName("All JUnit Advanced Exercises – Full Suite")
@SelectClasses({
    // Basic exercises (JUnit 4, run via Vintage engine)
    CalculatorTest.class,
    AssertionsTest.class,
    BankAccountTest.class,

    // Advanced exercises (JUnit 5)
    EvenCheckerTest.class,
    OrderedTests.class,
    ExceptionThrowerTest.class,
    PerformanceTesterTest.class
})
public class AllTests {
    /*
     * This class intentionally left empty.
     * All configuration is expressed through annotations above.
     */
}
