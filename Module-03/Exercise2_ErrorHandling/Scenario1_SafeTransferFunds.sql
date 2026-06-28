--------------------------------------------------------------------------------
-- Exercise 2, Scenario 1
-- SafeTransferFunds: transfers funds between two accounts. If any error
-- occurs (e.g., insufficient funds, invalid account), log an error message
-- and roll back the transaction.
--------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
) IS
    v_from_balance      NUMBER;
    insufficient_funds  EXCEPTION;
BEGIN
    -- Lock the source row so concurrent transfers can't overdraw it
    SELECT Balance INTO v_from_balance
    FROM Accounts
    WHERE AccountID = p_from_account
    FOR UPDATE;

    IF v_from_balance < p_amount THEN
        RAISE insufficient_funds;
    END IF;

    UPDATE Accounts
    SET Balance = Balance - p_amount, LastModified = SYSDATE
    WHERE AccountID = p_from_account;

    UPDATE Accounts
    SET Balance = Balance + p_amount, LastModified = SYSDATE
    WHERE AccountID = p_to_account;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20010, 'Destination account ' || p_to_account || ' does not exist.');
    END IF;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer of ' || p_amount || ' from Account ' || p_from_account ||
                          ' to Account ' || p_to_account || ' completed successfully.');

EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in Account ' || p_from_account ||
                              '. Transaction rolled back.');
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Source account ' || p_from_account ||
                              ' does not exist. Transaction rolled back.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM || '. Transaction rolled back.');
END SafeTransferFunds;
/

-- Example test calls:
-- EXEC SafeTransferFunds(1, 2, 200);     -- normal transfer
-- EXEC SafeTransferFunds(1, 2, 999999);  -- triggers insufficient funds error
-- EXEC SafeTransferFunds(999, 2, 100);   -- triggers invalid source account error
