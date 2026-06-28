--------------------------------------------------------------------------------
-- Exercise 5, Scenario 3
-- CheckTransactionRules: ensures withdrawals do not exceed the account
-- balance and deposits are positive before inserting a record into the
-- Transactions table.
--------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = :NEW.AccountID;

    IF :NEW.TransactionType = 'Withdrawal' AND :NEW.Amount > v_balance THEN
        RAISE_APPLICATION_ERROR(-20002, 'Withdrawal amount exceeds the account balance.');
    ELSIF :NEW.TransactionType = 'Withdrawal' AND :NEW.Amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Withdrawal amount must be positive.');
    ELSIF :NEW.TransactionType = 'Deposit' AND :NEW.Amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 'Deposit amount must be positive.');
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20005, 'Account ' || :NEW.AccountID || ' does not exist.');
END CheckTransactionRules;
/

-- Example tests:
-- INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
-- VALUES (4, 1, SYSDATE, 100, 'Deposit');               -- succeeds
--
-- INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
-- VALUES (5, 1, SYSDATE, 999999, 'Withdrawal');         -- fails: exceeds balance
--
-- INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
-- VALUES (6, 1, SYSDATE, -50, 'Deposit');                -- fails: deposit must be positive
