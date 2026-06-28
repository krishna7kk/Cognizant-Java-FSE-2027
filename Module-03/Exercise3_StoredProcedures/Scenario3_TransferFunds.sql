--------------------------------------------------------------------------------
-- Exercise 3, Scenario 3
-- TransferFunds: transfers a specified amount from one account to another,
-- checking that the source account has sufficient balance before transferring.
--------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
) IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = p_from_account;

    IF v_balance >= p_amount THEN
        UPDATE Accounts SET Balance = Balance - p_amount, LastModified = SYSDATE
        WHERE AccountID = p_from_account;

        UPDATE Accounts SET Balance = Balance + p_amount, LastModified = SYSDATE
        WHERE AccountID = p_to_account;

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Transferred ' || p_amount || ' from Account ' || p_from_account ||
                              ' to Account ' || p_to_account || '.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Transfer failed: Insufficient balance in Account ' || p_from_account || '.');
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: Source account ' || p_from_account || ' not found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error during transfer: ' || SQLERRM);
END TransferFunds;
/

-- Example test calls:
-- EXEC TransferFunds(1, 2, 100);     -- normal transfer
-- EXEC TransferFunds(1, 2, 999999);  -- insufficient balance
