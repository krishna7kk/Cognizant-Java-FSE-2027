--------------------------------------------------------------------------------
-- Exercise 1, Scenario 3
-- Fetch all loans due within the next 30 days and print a reminder message
-- for each customer.
--------------------------------------------------------------------------------

DECLARE
    CURSOR loan_cursor IS
        SELECT l.LoanID, l.CustomerID, l.EndDate, c.Name
        FROM   Loans l
        JOIN   Customers c ON l.CustomerID = c.CustomerID
        WHERE  l.EndDate BETWEEN SYSDATE AND SYSDATE + 30;
BEGIN
    FOR loan_rec IN loan_cursor LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Dear ' || loan_rec.Name ||
                              ', your loan (ID: ' || loan_rec.LoanID ||
                              ') is due on ' || TO_CHAR(loan_rec.EndDate, 'DD-MON-YYYY') || '.');
    END LOOP;
END;
/
