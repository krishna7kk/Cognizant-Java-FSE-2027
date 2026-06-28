package com.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Exercise 2: Verifying Interactions
 *
 * Core concepts:
 *   verify(mock).method()          – asserts the method was called exactly once
 *   verify(mock, times(n)).method() – asserts exactly n calls
 *   verify(mock, never()).method()  – asserts the method was NEVER called
 *   verify(mock, atLeastOnce())     – asserts ≥ 1 call
 *   verify(mock, atMost(n))         – asserts ≤ n calls
 *
 * Why verify?
 *   Assertions check RETURN VALUES.
 *   Verification checks BEHAVIOUR – did our code actually talk to the dependency?
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 2: Verifying Interactions")
class Ex2_VerifyingInteractionsTest {

    @Mock
    ExternalApi mockApi;

    // ---------------------------------------------------------------
    // 2a. Basic verify – method called exactly once
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchData() calls getData() exactly once")
    void fetchDataCallsGetDataOnce() {
        // Arrange
        MyService service = new MyService(mockApi);

        // Act
        service.fetchData();

        // Assert – verify that getData() was called exactly 1 time
        verify(mockApi).getData();                  // shorthand for times(1)
        verify(mockApi, times(1)).getData();        // explicit equivalent
    }

    // ---------------------------------------------------------------
    // 2b. verify with times(n)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchMultiple(3) calls getData() exactly 3 times")
    void fetchMultipleCallsGetDataThreeTimes() {
        // Arrange
        when(mockApi.getData()).thenReturn("item");
        MyService service = new MyService(mockApi);

        // Act
        service.fetchMultiple(3);

        // Assert
        verify(mockApi, times(3)).getData();
    }

    // ---------------------------------------------------------------
    // 2c. verify never()
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchData() never calls sendData()")
    void fetchDataNeverCallsSendData() {
        MyService service = new MyService(mockApi);

        service.fetchData();

        verify(mockApi, never()).sendData(anyString());
    }

    // ---------------------------------------------------------------
    // 2d. atLeastOnce / atMost
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchMultiple(5) calls getData() at least once and at most 5 times")
    void fetchMultipleBoundedCallCount() {
        when(mockApi.getData()).thenReturn("data");
        MyService service = new MyService(mockApi);

        service.fetchMultiple(5);

        verify(mockApi, atLeastOnce()).getData();
        verify(mockApi, atMost(5)).getData();
    }

    // ---------------------------------------------------------------
    // 2e. verifyNoMoreInteractions – nothing else was called
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchData() causes no interactions beyond getData()")
    void noUnexpectedInteractions() {
        MyService service = new MyService(mockApi);

        service.fetchData();

        verify(mockApi).getData();
        verifyNoMoreInteractions(mockApi);  // fails if any other method was called
    }
}
