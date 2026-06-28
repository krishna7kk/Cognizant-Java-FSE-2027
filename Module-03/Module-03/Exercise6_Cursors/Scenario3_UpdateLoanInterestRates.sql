--------------------------------------------------------------------------------
-- Exercise 6, Scenario 3
-- UpdateLoanInterestRates: explicit cursor that fetches all loans and
-- updates their interest rates based on a new bank policy.
--------------------------------------------------------------------------------

DECLARE
    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID, InterestRate
        FROM   Loans
        FOR UPDATE;

    v_new_policy_increase CONSTANT NUMBER := 0.5;  -- percentage points added under new policy
BEGIN
    FOR loan_rec IN UpdateLoanInterestRates LOOP
        UPDATE Loans
        SET InterestRate = InterestRate + v_new_policy_increase
        WHERE LoanID = loan_rec.LoanID;

        DBMS_OUTPUT.PUT_LINE('LoanID ' || loan_rec.LoanID || ' interest rate updated from ' ||
                              loan_rec.InterestRate || '% to ' ||
                              (loan_rec.InterestRate + v_new_policy_increase) || '%.');
    END LOOP;

    COMMIT;
END;
/
