--------------------------------------------------------------------------------
-- Exercise 4, Scenario 2
-- CalculateMonthlyInstallment: takes the loan amount, annual interest rate
-- (as a percentage, e.g. 5 for 5%), and loan duration in years, and returns
-- the monthly installment amount using the standard amortization formula:
--
--     M = P * r * (1 + r)^n / ((1 + r)^n - 1)
--
-- where P = principal, r = monthly interest rate, n = number of monthly payments.
--------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_loan_amount IN NUMBER,
    p_annual_rate IN NUMBER,   -- e.g. 5 means 5% per year
    p_years       IN NUMBER
) RETURN NUMBER IS
    v_monthly_rate NUMBER;
    v_num_payments NUMBER;
    v_installment  NUMBER;
BEGIN
    v_monthly_rate := (p_annual_rate / 100) / 12;
    v_num_payments := p_years * 12;

    IF v_monthly_rate = 0 THEN
        v_installment := p_loan_amount / v_num_payments;
    ELSE
        v_installment := p_loan_amount * v_monthly_rate * POWER(1 + v_monthly_rate, v_num_payments)
                         / (POWER(1 + v_monthly_rate, v_num_payments) - 1);
    END IF;

    RETURN ROUND(v_installment, 2);
END CalculateMonthlyInstallment;
/

-- Example test calls:
-- SELECT CalculateMonthlyInstallment(5000, 5, 5) AS MonthlyPayment FROM DUAL;
-- SELECT LoanID, CalculateMonthlyInstallment(LoanAmount, InterestRate,
--        ROUND(MONTHS_BETWEEN(EndDate, StartDate)/12)) AS MonthlyPayment
-- FROM Loans;
