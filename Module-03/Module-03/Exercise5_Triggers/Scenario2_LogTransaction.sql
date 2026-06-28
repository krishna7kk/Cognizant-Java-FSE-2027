--------------------------------------------------------------------------------
-- Exercise 5, Scenario 2
-- LogTransaction: inserts a record into an AuditLog table whenever a
-- transaction is inserted into the Transactions table.
--
-- NOTE: The AuditLog table is already created in 00_Schema/01_create_tables.sql.
-- It is repeated here (commented out) for reference in case this script is
-- run standalone.
--------------------------------------------------------------------------------

-- CREATE TABLE AuditLog (
--     AuditID         NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--     TransactionID   NUMBER,
--     AccountID       NUMBER,
--     TransactionDate DATE,
--     Amount          NUMBER,
--     TransactionType VARCHAR2(10),
--     LoggedDate      DATE
-- );

CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (TransactionID, AccountID, TransactionDate, Amount, TransactionType, LoggedDate)
    VALUES (:NEW.TransactionID, :NEW.AccountID, :NEW.TransactionDate, :NEW.Amount, :NEW.TransactionType, SYSDATE);
END LogTransaction;
/

-- Example test:
-- INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
-- VALUES (3, 1, SYSDATE, 150, 'Deposit');
-- SELECT * FROM AuditLog;
