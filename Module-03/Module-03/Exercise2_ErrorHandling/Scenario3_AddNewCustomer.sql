--------------------------------------------------------------------------------
-- Exercise 2, Scenario 3
-- AddNewCustomer: inserts a new customer into the Customers table. If a
-- customer with the same ID already exists, handle the exception by logging
-- an error and preventing the insertion.
--------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_customer_id IN NUMBER,
    p_name        IN VARCHAR2,
    p_dob         IN DATE,
    p_balance     IN NUMBER
) IS
    duplicate_customer EXCEPTION;
    v_count             NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM Customers
    WHERE CustomerID = p_customer_id;

    IF v_count > 0 THEN
        RAISE duplicate_customer;
    END IF;

    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer ' || p_name || ' (ID: ' || p_customer_id || ') added successfully.');

EXCEPTION
    WHEN duplicate_customer THEN
        DBMS_OUTPUT.PUT_LINE('Error: Customer with ID ' || p_customer_id ||
                              ' already exists. Insertion prevented.');
    WHEN DUP_VAL_ON_INDEX THEN
        -- Safety net in case of a race condition past the COUNT(*) check
        DBMS_OUTPUT.PUT_LINE('Error: Duplicate CustomerID ' || p_customer_id ||
                              ' detected by primary key constraint. Insertion prevented.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error adding customer: ' || SQLERRM);
END AddNewCustomer;
/

-- Example test calls:
-- EXEC AddNewCustomer(10, 'New Customer', TO_DATE('1995-01-01','YYYY-MM-DD'), 500);
-- EXEC AddNewCustomer(1, 'Duplicate Test', TO_DATE('1995-01-01','YYYY-MM-DD'), 500); -- already exists
