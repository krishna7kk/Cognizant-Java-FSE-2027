--------------------------------------------------------------------------------
-- Exercise 2, Scenario 2
-- UpdateSalary: increases the salary of an employee by a given percentage.
-- If the employee ID does not exist, handle the exception and log an error.
--------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_employee_id IN NUMBER,
    p_percentage  IN NUMBER
) IS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM Employees
    WHERE EmployeeID = p_employee_id;

    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Employee ID ' || p_employee_id || ' does not exist.');
    END IF;

    UPDATE Employees
    SET Salary = Salary + (Salary * p_percentage / 100)
    WHERE EmployeeID = p_employee_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated successfully for EmployeeID: ' || p_employee_id ||
                          ' (increase of ' || p_percentage || '%).');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error updating salary: ' || SQLERRM);
END UpdateSalary;
/

-- Example test calls:
-- EXEC UpdateSalary(1, 10);    -- valid employee, 10% raise
-- EXEC UpdateSalary(999, 10);  -- invalid employee, logs an error
