package com.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Exercise 6: Verifying Interaction Order
 *
 * Core concepts:
 *   InOrder inOrder = inOrder(mock1, mock2, ...)
 *   inOrder.verify(mock1).firstMethod();
 *   inOrder.verify(mock2).secondMethod();
 *
 *   – verify() on a plain mock only checks that a call happened; it says
 *     nothing about ORDER relative to other calls.
 *   – InOrder is Mockito's tool to assert a strict sequence of calls.
 *   – You can pass multiple mocks to inOrder() to verify order across them.
 *
 * Use-case: workflows where operations must follow a protocol
 *   (e.g. connect → authenticate → query → close).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 6: Verifying Interaction Order")
class Ex6_InteractionOrderTest {

    @Mock
    ExternalApi mockApi;

    // ---------------------------------------------------------------
    // 6a. Basic InOrder – two calls on the same mock
    // ---------------------------------------------------------------

    @Test
    @DisplayName("fetchThenSend() calls getData() before sendData()")
    void fetchHappensBeforeSend() {
        // Arrange
        when(mockApi.getData()).thenReturn("fetched");
        MyService service = new MyService(mockApi);

        // Act
        service.fetchThenSend("payload");

        // Assert – create an InOrder verifier for the mock
        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).getData();           // must be FIRST
        inOrder.verify(mockApi).sendData("payload"); // must be SECOND
    }

    // ---------------------------------------------------------------
    // 6b. InOrder with times()
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Two sendMessage() calls happen in the order they were made")
    void twoSendCallsInOrder() {
        MyService service = new MyService(mockApi);

        service.sendMessage("alpha");
        service.sendMessage("beta");

        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).sendData("MSG:alpha");  // first
        inOrder.verify(mockApi).sendData("MSG:beta");   // second
    }

    // ---------------------------------------------------------------
    // 6c. InOrder across multiple mocks
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Actions on two separate mocks occur in the right sequence")
    void orderAcrossTwoMocks() {
        ExternalApi secondApi = mock(ExternalApi.class);

        // Simulate a service that orchestrates two APIs
        when(mockApi.getData()).thenReturn("primary");
        when(secondApi.getData()).thenReturn("secondary");

        // Act – call primary first, then secondary
        String primary   = mockApi.getData();
        String secondary = secondApi.getData();

        assertEquals("primary",   primary);
        assertEquals("secondary", secondary);

        // Assert order across both mocks
        InOrder inOrder = inOrder(mockApi, secondApi);
        inOrder.verify(mockApi).getData();    // primary first
        inOrder.verify(secondApi).getData(); // secondary second
    }

    // ---------------------------------------------------------------
    // 6d. InOrder with verifyNoMoreInteractions
    // ---------------------------------------------------------------

    @Test
    @DisplayName("No extra interactions occur after the expected sequence")
    void noExtraInteractionsAfterSequence() {
        when(mockApi.getData()).thenReturn("data");
        MyService service = new MyService(mockApi);

        service.fetchThenSend("msg");

        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).getData();
        inOrder.verify(mockApi).sendData("msg");
        inOrder.verifyNoMoreInteractions();   // nothing else on the mock
    }
}
