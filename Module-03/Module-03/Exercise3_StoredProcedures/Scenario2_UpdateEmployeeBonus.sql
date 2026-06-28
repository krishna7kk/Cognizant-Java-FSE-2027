--------------------------------------------------------------------------------
-- Exercise 3, Scenario 2
-- UpdateEmployeeBonus: updates the salary of employees in a given department
-- by adding a bonus percentage passed as a parameter.
--------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department IN VARCHAR2,
    p_bonus_pct  IN NUMBER
) IS
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * p_bonus_pct / 100)
    WHERE Department = p_department;

    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Bonus of ' || p_bonus_pct || '% applied to ' || SQL%ROWCOUNT ||
                              ' employee(s) in the ' || p_department || ' department.');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error updating employee bonus: ' || SQLERRM);
END UpdateEmployeeBonus;
/

-- Example test call:
-- EXEC UpdateEmployeeBonus('IT', 5);
