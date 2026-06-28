package com.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Exercise 4: Handling Void Methods
 *
 * Core concepts:
 *   Void methods cannot use when(...).thenReturn(...) because they return nothing.
 *   Mockito provides:
 *
 *   doNothing().when(mock).voidMethod()   – explicit no-op (default for void)
 *   doThrow(ex).when(mock).voidMethod()   – make it throw
 *   doAnswer(answer).when(mock).voidMethod() – run custom logic
 *
 *   verify(mock).voidMethod()  – works the same as for non-void methods
 *
 * Note: void methods are a no-op by default in Mockito, so doNothing() is
 * only needed when you want to be explicit or to set up after-the-fact
 * behaviour overrides.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 4: Handling Void Methods")
class Ex4_VoidMethodsTest {

    @Mock
    ExternalApi mockApi;

    // ---------------------------------------------------------------
    // 4a. Default behaviour – void method does nothing automatically
    // ---------------------------------------------------------------

    @Test
    @DisplayName("sendData() does nothing by default (Mockito default for void)")
    void voidMethodDoesNothingByDefault() {
        // Arrange – no stub needed; Mockito no-ops void methods automatically
        MyService service = new MyService(mockApi);

        // Act – should not throw or do anything real
        service.sendMessage("test");

        // Assert – verify the void method was called with the right payload
        verify(mockApi).sendData("MSG:test");
    }

    // ---------------------------------------------------------------
    // 4b. Explicit doNothing() stub (self-documenting intent)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Explicit doNothing() makes intent clear in the test")
    void explicitDoNothing() {
        // Arrange – explicit stub (same effect as the default)
        doNothing().when(mockApi).sendData(anyString());
        MyService service = new MyService(mockApi);

        // Act
        service.sendMessage("explicit");

        // Assert
        verify(mockApi).sendData("MSG:explicit");
    }

    // ---------------------------------------------------------------
    // 4c. doAnswer() – run custom logic inside the void method
    // ---------------------------------------------------------------

    @Test
    @DisplayName("doAnswer() lets us inspect args and run side-effect logic")
    void doAnswerOnVoidMethod() {
        // Capture what was "sent" via a side-effect in the answer
        java.util.List<String> captured = new java.util.ArrayList<>();

        doAnswer(invocation -> {
            String arg = invocation.getArgument(0);
            captured.add(arg);          // record the call
            return null;                // void method must return null
        }).when(mockApi).sendData(anyString());

        MyService service = new MyService(mockApi);

        // Act
        service.sendMessage("answer-demo");

        // Assert – our side-effect recorded the argument
        org.junit.jupiter.api.Assertions.assertEquals(1, captured.size());
        org.junit.jupiter.api.Assertions.assertEquals("MSG:answer-demo", captured.get(0));
    }

    // ---------------------------------------------------------------
    // 4d. Verify call count on void method
    // ---------------------------------------------------------------

    @Test
    @DisplayName("sendMessage() called twice → sendData() invoked exactly twice")
    void verifyVoidCallCount() {
        MyService service = new MyService(mockApi);

        service.sendMessage("first");
        service.sendMessage("second");

        verify(mockApi, times(2)).sendData(anyString());
        verify(mockApi).sendData("MSG:first");
        verify(mockApi).sendData("MSG:second");
    }
}
