# Module-03 — PL/SQL Practice Solutions (Banking Schema)

This package contains complete, runnable PL/SQL solutions for all 7 exercises
in the Module-03 assignment, built against the bank schema (Customers,
Accounts, Transactions, Loans, Employees).

## How to run

1. **Set up the schema first** (in order):
   - `00_Schema/01_create_tables.sql`
   - `00_Schema/02_sample_data.sql`

2. Run any exercise script independently afterward. Each `.sql` file is
   self-contained and includes example test calls (commented out) at the
   bottom so you can try it immediately.

3. Run scripts in SQL*Plus, SQL Developer, or any Oracle PL/SQL client.
   Make sure `SET SERVEROUTPUT ON` is enabled in your session so you can see
   `DBMS_OUTPUT.PUT_LINE` messages:
   ```sql
   SET SERVEROUTPUT ON;
   ```

## Folder structure

```
Module-03/
├── 00_Schema/
│   ├── 01_create_tables.sql        -- All 5 base tables + AuditLog table
│   └── 02_sample_data.sql          -- Sample rows (incl. extra rows for testing)
│
├── Exercise1_ControlStructures/
│   ├── Scenario1_LoanInterestDiscount.sql   -- 1% discount for customers > 60
│   ├── Scenario2_VIPStatus.sql              -- IsVIP flag for balance > $10,000
│   └── Scenario3_LoanDueReminders.sql       -- Reminders for loans due in 30 days
│
├── Exercise2_ErrorHandling/
│   ├── Scenario1_SafeTransferFunds.sql      -- PROCEDURE SafeTransferFunds
│   ├── Scenario2_UpdateSalary.sql           -- PROCEDURE UpdateSalary
│   └── Scenario3_AddNewCustomer.sql         -- PROCEDURE AddNewCustomer
│
├── Exercise3_StoredProcedures/
│   ├── Scenario1_ProcessMonthlyInterest.sql -- PROCEDURE ProcessMonthlyInterest
│   ├── Scenario2_UpdateEmployeeBonus.sql    -- PROCEDURE UpdateEmployeeBonus
│   └── Scenario3_TransferFunds.sql          -- PROCEDURE TransferFunds
│
├── Exercise4_Functions/
│   ├── Scenario1_CalculateAge.sql                  -- FUNCTION CalculateAge
│   ├── Scenario2_CalculateMonthlyInstallment.sql   -- FUNCTION CalculateMonthlyInstallment
│   └── Scenario3_HasSufficientBalance.sql          -- FUNCTION HasSufficientBalance
│
├── Exercise5_Triggers/
│   ├── Scenario1_UpdateCustomerLastModified.sql    -- TRIGGER UpdateCustomerLastModified
│   ├── Scenario2_LogTransaction.sql                -- TRIGGER LogTransaction
│   └── Scenario3_CheckTransactionRules.sql         -- TRIGGER CheckTransactionRules
│
├── Exercise6_Cursors/
│   ├── Scenario1_GenerateMonthlyStatements.sql     -- Explicit cursor: monthly statements
│   ├── Scenario2_ApplyAnnualFee.sql                -- Explicit cursor: annual fee deduction
│   └── Scenario3_UpdateLoanInterestRates.sql       -- Explicit cursor: policy rate update
│
└── Exercise7_Packages/
    ├── Scenario1_CustomerManagement_Package.sql    -- PACKAGE CustomerManagement
    ├── Scenario2_EmployeeManagement_Package.sql    -- PACKAGE EmployeeManagement
    └── Scenario3_AccountOperations_Package.sql     -- PACKAGE AccountOperations
```

## Notes & assumptions

- **Exercise 1, Scenario 2 (VIP flag):** The original schema didn't include an
  `IsVIP` column, so one was added to `Customers` in the schema script
  (`VARCHAR2(1)`, values `'Y'`/`'N'`).
- **Exercise 4, Scenario 3 (`HasSufficientBalance`):** This function returns a
  PL/SQL `BOOLEAN`, which Oracle does not allow to be selected directly from
  SQL or displayed by client tools. The script includes a small anonymous
  block at the bottom that calls the function and prints the result — use
  that to test it.
- **Exercise 5, Scenario 2 (`LogTransaction`):** Requires the `AuditLog` table,
  which is created in `00_Schema/01_create_tables.sql`.
- **Exercise 5, Scenario 3 (`CheckTransactionRules`):** This trigger and
  `LogTransaction` both fire on `INSERT` into `Transactions` — they coexist
  fine as independent triggers (rule validation happens `BEFORE INSERT`,
  audit logging happens `AFTER INSERT`).
- **Exercise 7, Scenario 2 (`CalculateAnnualSalary`):** Assumes the `Salary`
  column stores a *monthly* salary and multiplies by 12. If your data
  actually represents an annual figure already, simply remove the `* 12`.
- All procedures use `COMMIT`/`ROLLBACK` and exception handling
  (`EXCEPTION WHEN ... THEN`) to keep transactions safe, per the assignment's
  error-handling requirements.
