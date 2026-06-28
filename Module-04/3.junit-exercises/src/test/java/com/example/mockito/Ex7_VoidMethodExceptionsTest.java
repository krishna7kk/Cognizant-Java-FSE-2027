package com.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 7: Handling Void Methods with Exceptions
 *
 * Core concepts:
 *   doThrow(ExceptionType.class).when(mock).voidMethod()
 *   doThrow(new ConcreteException("msg")).when(mock).voidMethod()
 *
 *   – Because void methods have no return value, when(...).thenThrow(...)
 *     cannot be used (there is no return value to "chain" from).
 *   – doThrow() is the correct Mockito idiom for void methods.
 *
 *   Chaining:
 *   doNothing().doThrow(new RuntimeException()).when(mock).method()
 *     – first call succeeds, second call throws
 *
 * Use-case: network failures, permission errors, resource-not-found
 *   scenarios that your service must handle gracefully.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 7: Void Methods with Exceptions")
class Ex7_VoidMethodExceptionsTest {

    @Mock
    ExternalApi mockApi;

    // ---------------------------------------------------------------
    // 7a. doThrow with exception class
    // ---------------------------------------------------------------

    @Test
    @DisplayName("deleteById() throwing causes deleteResource() to wrap and rethrow")
    void deleteResourcePropagatesException() {
        // Arrange – stub deleteById(5) to throw
        doThrow(IllegalStateException.class).when(mockApi).deleteById(5);
        MyService service = new MyService(mockApi);

        // Act + Assert – MyService wraps it in RuntimeException
        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> service.deleteResource(5)
        );

        assertTrue(ex.getMessage().contains("5"),
            "Wrapper message should mention the resource ID");
        assertInstanceOf(IllegalStateException.class, ex.getCause(),
            "Root cause should be the original IllegalStateException");
    }

    // ---------------------------------------------------------------
    // 7b. doThrow with a concrete exception instance (with a message)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Exception message from the mock is preserved in the cause")
    void exceptionMessagePreserved() {
        // Arrange
        doThrow(new IllegalStateException("Resource 99 not found"))
                .when(mockApi).deleteById(99);
        MyService service = new MyService(mockApi);

        // Act
        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> service.deleteResource(99)
        );

        // Assert on the cause message
        assertEquals("Resource 99 not found", ex.getCause().getMessage());
    }

    // ---------------------------------------------------------------
    // 7c. Verify the void method was still called even though it threw
    // ---------------------------------------------------------------

    @Test
    @DisplayName("deleteById() is still invoked even when it throws")
    void mockMethodCalledBeforeException() {
        doThrow(RuntimeException.class).when(mockApi).deleteById(anyInt());
        MyService service = new MyService(mockApi);

        assertThrows(RuntimeException.class, () -> service.deleteResource(7));

        // The mock WAS called with the right argument before throwing
        verify(mockApi).deleteById(7);
    }

    // ---------------------------------------------------------------
    // 7d. sendData() – first call succeeds, second throws
    // ---------------------------------------------------------------

    @Test
    @DisplayName("sendData() succeeds on first call, throws on second")
    void firstCallSucceedsSecondThrows() {
        doNothing()
            .doThrow(new RuntimeException("Connection lost"))
            .when(mockApi).sendData(anyString());

        MyService service = new MyService(mockApi);

        // First call – no exception
        assertDoesNotThrow(() -> service.sendMessage("first"));

        // Second call – RuntimeException
        assertThrows(RuntimeException.class, () -> service.sendMessage("second"));

        // Verify both calls happened
        verify(mockApi, times(2)).sendData(anyString());
    }

    // ---------------------------------------------------------------
    // 7e. Happy path – verify no exception when stub does nothing
    // ---------------------------------------------------------------

    @Test
    @DisplayName("deleteResource() succeeds when deleteById() does not throw")
    void deleteResourceSucceedsWhenNoException() {
        doNothing().when(mockApi).deleteById(anyInt());
        MyService service = new MyService(mockApi);

        // Should complete without throwing
        assertDoesNotThrow(() -> service.deleteResource(1));

        verify(mockApi).deleteById(1);
    }
}
