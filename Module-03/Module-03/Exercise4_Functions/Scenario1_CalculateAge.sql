--------------------------------------------------------------------------------
-- Exercise 4, Scenario 1
-- CalculateAge: takes a customer's date of birth as input and returns their
-- age in years.
--------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION CalculateAge (
    p_dob IN DATE
) RETURN NUMBER IS
    v_age NUMBER;
BEGIN
    v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
    RETURN v_age;
END CalculateAge;
/

-- Example test calls:
-- SELECT CalculateAge(DOB) AS Age FROM Customers;
-- SELECT CalculateAge(TO_DATE('1985-05-15','YYYY-MM-DD')) AS Age FROM DUAL;
