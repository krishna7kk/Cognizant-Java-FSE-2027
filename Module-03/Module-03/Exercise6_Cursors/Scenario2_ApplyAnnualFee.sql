--------------------------------------------------------------------------------
-- Exercise 6, Scenario 2
-- ApplyAnnualFee: explicit cursor that deducts an annual maintenance fee
-- from the balance of all accounts.
--------------------------------------------------------------------------------

DECLARE
    CURSOR ApplyAnnualFee IS
        SELECT AccountID, Balance
        FROM   Accounts
        FOR UPDATE;

    v_annual_fee CONSTANT NUMBER := 25;  -- flat annual maintenance fee
BEGIN
    FOR acct_rec IN ApplyAnnualFee LOOP
        UPDATE Accounts
        SET Balance      = Balance - v_annual_fee,
            LastModified = SYSDATE
        WHERE AccountID = acct_rec.AccountID;

        DBMS_OUTPUT.PUT_LINE('Annual fee of ' || v_annual_fee || ' deducted from Account ' ||
                              acct_rec.AccountID || '. New balance: ' ||
                              (acct_rec.Balance - v_annual_fee));
    END LOOP;

    COMMIT;
END;
/
