--------------------------------------------------------------------------------
-- Exercise 3, Scenario 1
-- ProcessMonthlyInterest: calculates and updates the balance of all savings
-- accounts by applying a 1% interest rate to the current balance.
--------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest IS
    v_interest_rate CONSTANT NUMBER := 0.01;  -- 1%
BEGIN
    UPDATE Accounts
    SET Balance      = Balance + (Balance * v_interest_rate),
        LastModified = SYSDATE
    WHERE AccountType = 'Savings';

    DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT || ' savings account(s) updated with monthly interest.');

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error processing monthly interest: ' || SQLERRM);
END ProcessMonthlyInterest;
/

-- Example test call:
-- EXEC ProcessMonthlyInterest;
