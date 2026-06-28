--------------------------------------------------------------------------------
-- Exercise 7, Scenario 1
-- CustomerManagement package: groups customer-related procedures/functions
--   - AddCustomer: adds a new customer
--   - UpdateCustomerDetails: updates a customer's name/balance
--   - GetCustomerBalance: returns a customer's current balance
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE CustomerManagement AS

    PROCEDURE AddCustomer (
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_dob         IN DATE,
        p_balance     IN NUMBER
    );

    PROCEDURE UpdateCustomerDetails (
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2 DEFAULT NULL,
        p_balance     IN NUMBER DEFAULT NULL
    );

    FUNCTION GetCustomerBalance (
        p_customer_id IN NUMBER
    ) RETURN NUMBER;

END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddCustomer (
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_dob         IN DATE,
        p_balance     IN NUMBER
    ) IS
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Customer ' || p_name || ' added successfully.');

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Error: Customer ID ' || p_customer_id || ' already exists.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error adding customer: ' || SQLERRM);
    END AddCustomer;


    PROCEDURE UpdateCustomerDetails (
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2 DEFAULT NULL,
        p_balance     IN NUMBER DEFAULT NULL
    ) IS
    BEGIN
        UPDATE Customers
        SET Name         = COALESCE(p_name, Name),
            Balance      = COALESCE(p_balance, Balance),
            LastModified = SYSDATE
        WHERE CustomerID = p_customer_id;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Error: Customer ID ' || p_customer_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Customer ID ' || p_customer_id || ' updated successfully.');
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error updating customer: ' || SQLERRM);
    END UpdateCustomerDetails;


    FUNCTION GetCustomerBalance (
        p_customer_id IN NUMBER
    ) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance
        FROM Customers
        WHERE CustomerID = p_customer_id;

        RETURN v_balance;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN NULL;
    END GetCustomerBalance;

END CustomerManagement;
/

-- Example test calls:
-- EXEC CustomerManagement.AddCustomer(20, 'Test User', TO_DATE('1992-01-01','YYYY-MM-DD'), 1200);
-- EXEC CustomerManagement.UpdateCustomerDetails(20, p_balance => 2000);
-- SELECT CustomerManagement.GetCustomerBalance(20) FROM DUAL;
