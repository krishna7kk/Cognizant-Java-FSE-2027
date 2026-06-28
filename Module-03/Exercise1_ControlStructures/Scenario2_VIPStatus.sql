--------------------------------------------------------------------------------
-- Exercise 1, Scenario 2
-- Iterate through all customers and set IsVIP = 'Y' for those with a balance
-- over $10,000.
--
-- NOTE: This requires the IsVIP column added in 00_Schema/01_create_tables.sql.
-- If you are running against an existing Customers table without this column,
-- uncomment the ALTER TABLE statement below first.
--------------------------------------------------------------------------------

-- ALTER TABLE Customers ADD (IsVIP VARCHAR2(1) DEFAULT 'N');

DECLARE
    CURSOR cust_cursor IS
        SELECT CustomerID, Balance FROM Customers;
BEGIN
    FOR cust_rec IN cust_cursor LOOP
        IF cust_rec.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'Y'
            WHERE CustomerID = cust_rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE('CustomerID ' || cust_rec.CustomerID || ' flagged as VIP.');
        ELSE
            UPDATE Customers
            SET IsVIP = 'N'
            WHERE CustomerID = cust_rec.CustomerID;
        END IF;
    END LOOP;

    COMMIT;
END;
/
