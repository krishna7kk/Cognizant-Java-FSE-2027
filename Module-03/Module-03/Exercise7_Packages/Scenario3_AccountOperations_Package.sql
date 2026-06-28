--------------------------------------------------------------------------------
-- Exercise 7, Scenario 3
-- AccountOperations package: groups account-related operations
--   - OpenAccount: opens a new account for a customer
--   - CloseAccount: closes an existing account
--   - GetTotalBalance: returns a customer's total balance across all accounts
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE AccountOperations AS

    PROCEDURE OpenAccount (
        p_account_id      IN NUMBER,
        p_customer_id     IN NUMBER,
        p_account_type    IN VARCHAR2,
        p_initial_balance IN NUMBER DEFAULT 0
    );

    PROCEDURE CloseAccount (
        p_account_id IN NUMBER
    );

    FUNCTION GetTotalBalance (
        p_customer_id IN NUMBER
    ) RETURN NUMBER;

END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount (
        p_account_id      IN NUMBER,
        p_customer_id     IN NUMBER,
        p_account_type    IN VARCHAR2,
        p_initial_balance IN NUMBER DEFAULT 0
    ) IS
        v_cust_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_cust_count
        FROM Customers
        WHERE CustomerID = p_customer_id;

        IF v_cust_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20005, 'Customer ID ' || p_customer_id || ' does not exist.');
        END IF;

        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_account_id, p_customer_id, p_account_type, p_initial_balance, SYSDATE);

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Account ' || p_account_id || ' opened successfully for Customer ' ||
                              p_customer_id || '.');

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Error: Account ID ' || p_account_id || ' already exists.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error opening account: ' || SQLERRM);
    END OpenAccount;


    PROCEDURE CloseAccount (
        p_account_id IN NUMBER
    ) IS
    BEGIN
        DELETE FROM Accounts WHERE AccountID = p_account_id;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Error: Account ID ' || p_account_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Account ' || p_account_id || ' closed successfully.');
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error closing account: ' || SQLERRM);
    END CloseAccount;


    FUNCTION GetTotalBalance (
        p_customer_id IN NUMBER
    ) RETURN NUMBER IS
        v_total NUMBER;
    BEGIN
        SELECT SUM(Balance) INTO v_total
        FROM Accounts
        WHERE CustomerID = p_customer_id;

        RETURN NVL(v_total, 0);
    END GetTotalBalance;

END AccountOperations;
/

-- Example test calls:
-- EXEC AccountOperations.OpenAccount(10, 1, 'Savings', 0);
-- EXEC AccountOperations.CloseAccount(10);
-- SELECT AccountOperations.GetTotalBalance(1) FROM DUAL;
