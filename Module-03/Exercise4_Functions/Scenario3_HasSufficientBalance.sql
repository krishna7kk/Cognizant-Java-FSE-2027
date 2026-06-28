--------------------------------------------------------------------------------
-- Exercise 4, Scenario 3
-- HasSufficientBalance: takes an account ID and an amount as input and
-- returns a boolean indicating whether the account has at least the
-- specified amount.
--
-- NOTE: PL/SQL BOOLEAN cannot be returned directly to SQL or to client tools
-- (e.g. SQL*Plus, SQL Developer "Run") -- it can only be consumed from
-- another PL/SQL block. A test block is included below.
--------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION HasSufficientBalance (
    p_account_id IN NUMBER,
    p_amount     IN NUMBER
) RETURN BOOLEAN IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = p_account_id;

    RETURN (v_balance >= p_amount);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
END HasSufficientBalance;
/

-- Example test block (run this to see the result, since BOOLEAN can't be SELECTed):
DECLARE
    v_result BOOLEAN;
BEGIN
    v_result := HasSufficientBalance(1, 500);
    IF v_result THEN
        DBMS_OUTPUT.PUT_LINE('Account has sufficient balance.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Account does NOT have sufficient balance.');
    END IF;
END;
/
