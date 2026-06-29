# 3.Spring-Jpa-handson — HQL, JPQL, Native Query & Criteria Query

This project implements **all 6 Hands-On exercises** from the "Spring Data
JPA — Queries" training module. It builds on the same `orm-learn` Spring
Boot project as the previous module (Country CRUD is still present and
still runs), and adds two new feature areas: **Employee/Department/Skill**
and a **Quiz Attempt** schema.

| Hands On | Topic | Where it lives |
|---|---|---|
| 1 | HQL vs JPQL fundamentals | conceptual — `docs/handson1-hql-jpql-notes.md` |
| 2 | Get all permanent employees using HQL (with `join fetch`) | `EmployeeRepository.getAllPermanentEmployees()`, `EmployeeService`, `OrmLearnApplication.testGetAllPermanentEmployees()` |
| 3 | Fetch quiz attempt details using HQL | `model/{User,Question,Options,Attempt,AttemptQuestion,AttemptOption}.java`, `AttemptRepository`, `AttemptService`, `OrmLearnApplication.testGetAttemptDetail()` |
| 4 | Average salary using HQL aggregate function | `EmployeeRepository.getAverageSalaryByDepartment()`, `OrmLearnApplication.testGetAverageSalaryByDepartment()` |
| 5 | Get all employees using Native Query | `EmployeeRepository.getAllEmployeesNative()`, `OrmLearnApplication.testGetAllEmployeesNative()` |
| 6 | Criteria Query (dynamic filters) | conceptual + worked example — `docs/handson6-criteria-query-notes.md` |

## Prerequisites

- JDK 17
- Maven 3.6+
- MySQL Server 8.0 (the H2 fallback from the prior module still works for
  the Country exercises, but the Employee/Quiz schemas here are written
  for MySQL syntax in the setup scripts)

## Setup

1. Run the original Country setup (if you haven't already from the
   previous module):
   ```
   mysql -u root -p < setup.sql
   ```

2. Run the new Employee/Department/Skill schema + sample data:
   ```
   mysql -u root -p < setup-employee.sql
   ```

3. Run the new Quiz Attempt schema + sample data:
   ```
   mysql -u root -p < setup-quiz.sql
   ```

   > **Note on `quiz.mwb`:** the original handout has you generate this
   > schema's SQL from a MySQL Workbench model file (`quiz.mwb`) via
   > *Forward Engineer SQL CREATE Script*. That binary `.mwb` file isn't
   > something I have access to, so `setup-quiz.sql` defines an equivalent
   > schema by hand instead, using the same `user → attempt →
   > attempt_question → question`, `attempt_question → attempt_option →
   > options` relationships described in the handout, with column-naming
   > consistent with the rest of the project (`us_*`, `qu_*`, `op_*`,
   > `at_*`, `aq_*`, `ao_*`). The sample data reproduces the exact 4
   > HTML-quiz questions/options/scores shown in the handout's expected
   > output. If you do have the real `quiz.mwb` file, you can forward-engineer
   > it instead and adjust the entity `@Column` mappings in
   > `model/User.java`, `Question.java`, `Options.java`, `Attempt.java`,
   > `AttemptQuestion.java`, and `AttemptOption.java` to match its actual
   > column names.

4. Build and run:
   ```
   mvn clean package
   mvn spring-boot:run
   ```

## What you'll see in the console

In addition to all the Country CRUD tests from the previous module, this
run adds:

1. **`testGetAllPermanentEmployees()`** — logs every permanent employee
   along with their department and skill list, all fetched in a single SQL
   query (`left join fetch`).
2. **`testGetAttemptDetail()`** — prints attempt #1 for user #1 in the
   handout's exact format:
   ```
   What is the extension of the hyper text markup language file?
    1) .xhtm       0.0     false
    2) .ht         0.0     false
    3) .html       1.0     true
    4) .htmx       0.0     false
   ```
   (repeated for all 4 questions in the seeded attempt)
3. **`testGetAverageSalaryByDepartment()`** — logs the average salary for
   department id 1 (Engineering), computed via an HQL `AVG()` aggregate.
4. **`testGetAllEmployeesNative()`** — logs all employees fetched via a raw
   native SQL query instead of HQL.

Enable `logging.level.org.hibernate.SQL=trace` (already set in
`application.properties`) to see exactly how many SQL statements each
approach generates — this is the core lesson of Hands On 2: naive HQL +
entity-level `EAGER` fetch can produce one query per related row (N+1
problem), while `left join fetch` at the query level produces a single
SQL statement.

## Key files added in this module

```
3.Spring-Jpa-handson/
├── setup-employee.sql          <- Department/Skill/Employee schema + data
├── setup-quiz.sql              <- User/Question/Options/Attempt schema + data
├── docs/
│   ├── handson1-hql-jpql-notes.md        <- this module's Hands On 1
│   ├── handson6-criteria-query-notes.md  <- this module's Hands On 6
│   └── prior-module-*.md                 <- carried over from the previous
│                                             module (Hibernate XML/Annotation
│                                             config, JPA vs Hibernate) - kept
│                                             for reference, not part of this
│                                             module's numbered hands-on list
└── src/main/java/com/cognizant/ormlearn/
    ├── model/
    │   ├── Department.java
    │   ├── Skill.java
    │   ├── Employee.java
    │   ├── User.java
    │   ├── Question.java
    │   ├── Options.java
    │   ├── Attempt.java
    │   ├── AttemptQuestion.java
    │   └── AttemptOption.java
    ├── repository/
    │   ├── DepartmentRepository.java
    │   ├── SkillRepository.java
    │   ├── EmployeeRepository.java       <- HQL, JPQL aggregate, native query
    │   └── AttemptRepository.java        <- HQL with multi-table join fetch
    └── service/
        ├── EmployeeService.java
        └── AttemptService.java
```

## Note on Java Persistence imports

Same as the prior module: this project targets **Spring Boot 2.7.x** with
`javax.persistence.*` annotations to match the handout exactly. If you
upgrade to Spring Boot 3.x, switch all `javax.persistence.*` imports to
`jakarta.persistence.*`.
