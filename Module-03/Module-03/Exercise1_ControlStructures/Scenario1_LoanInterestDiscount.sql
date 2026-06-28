--------------------------------------------------------------------------------
-- Exercise 1, Scenario 1
-- Loop through all customers; if age > 60, apply a 1% discount to their
-- current loan interest rate(s).
--------------------------------------------------------------------------------

DECLARE
    CURSOR cust_cursor IS
        SELECT CustomerID, DOB FROM Customers;
    v_age NUMBER;
BEGIN
    FOR cust_rec IN cust_cursor LOOP
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, cust_rec.DOB) / 12);

        IF v_age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - (InterestRate * 0.01)
            WHERE CustomerID = cust_rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE('Applied 1% discount to loan(s) for CustomerID: '
                                  || cust_rec.CustomerID || ' (Age: ' || v_age || ')');
        END IF;
    END LOOP;

    COMMIT;
END;
/
