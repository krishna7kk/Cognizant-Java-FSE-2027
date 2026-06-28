--------------------------------------------------------------------------------
-- Exercise 5, Scenario 1
-- UpdateCustomerLastModified: updates the LastModified column of the
-- Customers table to the current date whenever a customer's record is updated.
--------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON Customers
FOR EACH ROW
BEGIN
    :NEW.LastModified := SYSDATE;
END UpdateCustomerLastModified;
/

-- Example test:
-- UPDATE Customers SET Balance = Balance + 50 WHERE CustomerID = 1;
-- SELECT CustomerID, LastModified FROM Customers WHERE CustomerID = 1;
