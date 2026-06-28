--------------------------------------------------------------------------------
-- Exercise 7, Scenario 2
-- EmployeeManagement package: groups employee-related procedures/functions
--   - HireEmployee: hires a new employee
--   - UpdateEmployeeDetails: updates position/salary/department
--   - CalculateAnnualSalary: returns an employee's annual salary
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE EmployeeManagement AS

    PROCEDURE HireEmployee (
        p_employee_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_position    IN VARCHAR2,
        p_salary      IN NUMBER,
        p_department  IN VARCHAR2
    );

    PROCEDURE UpdateEmployeeDetails (
        p_employee_id IN NUMBER,
        p_position    IN VARCHAR2 DEFAULT NULL,
        p_salary      IN NUMBER DEFAULT NULL,
        p_department  IN VARCHAR2 DEFAULT NULL
    );

    FUNCTION CalculateAnnualSalary (
        p_employee_id IN NUMBER
    ) RETURN NUMBER;

END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee (
        p_employee_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_position    IN VARCHAR2,
        p_salary      IN NUMBER,
        p_department  IN VARCHAR2
    ) IS
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (p_employee_id, p_name, p_position, p_salary, p_department, SYSDATE);

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Employee ' || p_name || ' hired successfully.');

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_employee_id || ' already exists.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error hiring employee: ' || SQLERRM);
    END HireEmployee;


    PROCEDURE UpdateEmployeeDetails (
        p_employee_id IN NUMBER,
        p_position    IN VARCHAR2 DEFAULT NULL,
        p_salary      IN NUMBER DEFAULT NULL,
        p_department  IN VARCHAR2 DEFAULT NULL
    ) IS
    BEGIN
        UPDATE Employees
        SET Position   = COALESCE(p_position, Position),
            Salary      = COALESCE(p_salary, Salary),
            Department  = COALESCE(p_department, Department)
        WHERE EmployeeID = p_employee_id;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_employee_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Employee ID ' || p_employee_id || ' updated successfully.');
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error updating employee: ' || SQLERRM);
    END UpdateEmployeeDetails;


    FUNCTION CalculateAnnualSalary (
        p_employee_id IN NUMBER
    ) RETURN NUMBER IS
        v_monthly_salary NUMBER;
    BEGIN
        SELECT Salary INTO v_monthly_salary
        FROM Employees
        WHERE EmployeeID = p_employee_id;

        RETURN v_monthly_salary * 12;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN NULL;
    END CalculateAnnualSalary;

END EmployeeManagement;
/

-- Example test calls:
-- EXEC EmployeeManagement.HireEmployee(10, 'New Hire', 'Analyst', 50000, 'Finance');
-- EXEC EmployeeManagement.UpdateEmployeeDetails(10, p_salary => 55000);
-- SELECT EmployeeManagement.CalculateAnnualSalary(10) FROM DUAL;
