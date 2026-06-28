--------------------------------------------------------------------------------
-- Exercise 6, Scenario 1
-- GenerateMonthlyStatements: explicit cursor that retrieves all transactions
-- for the current month and prints a statement for each customer.
--------------------------------------------------------------------------------

DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT c.CustomerID, c.Name, t.TransactionID, t.TransactionDate,
               t.Amount, t.TransactionType, a.AccountID
        FROM   Transactions t
        JOIN   Accounts a ON t.AccountID = a.AccountID
        JOIN   Customers c ON a.CustomerID = c.CustomerID
        WHERE  TRUNC(t.TransactionDate, 'MM') = TRUNC(SYSDATE, 'MM')
        ORDER BY c.CustomerID, t.TransactionDate;

    v_current_customer NUMBER := -1;
BEGIN
    FOR txn_rec IN GenerateMonthlyStatements LOOP
        IF v_current_customer != txn_rec.CustomerID THEN
            DBMS_OUTPUT.PUT_LINE('--- Monthly Statement for ' || txn_rec.Name ||
                                  ' (CustomerID: ' || txn_rec.CustomerID || ') ---');
            v_current_customer := txn_rec.CustomerID;
        END IF;

        DBMS_OUTPUT.PUT_LINE('  TxnID: ' || txn_rec.TransactionID ||
                              ' | Account: ' || txn_rec.AccountID ||
                              ' | Date: ' || TO_CHAR(txn_rec.TransactionDate, 'DD-MON-YYYY') ||
                              ' | Type: ' || txn_rec.TransactionType ||
                              ' | Amount: ' || txn_rec.Amount);
    END LOOP;
END;
/
