# Hands On 1 — Introduction to HQL and JPQL

| | HQL | JPQL |
|---|---|---|
| Stands for | Hibernate Query Language | Java Persistence Query Language |
| Defined by | Hibernate (vendor-specific) | JPA specification (JSR 338) |
| Relationship | Superset — JPQL is a subset of HQL | Subset of HQL |
| Portability | Tied to Hibernate | Portable across any JPA provider |

Key points:

- Both HQL and JPQL are **object-focused** query languages, similar in
  syntax to SQL, but they operate on **entity names and field names**
  rather than table names and column names.
- **All valid JPQL queries are also valid HQL queries**, but the reverse is
  not true — HQL has extra features JPQL doesn't define.
- Both support `SELECT`, `UPDATE`, and `DELETE`.
- **HQL additionally supports `INSERT`** statements, which JPQL does not
  define.

In this project, every `@Query` value written in `EmployeeRepository` and
`AttemptRepository` (Hands On 2–4) is plain HQL — for example:

```java
@Query(value = "SELECT e FROM Employee e left join fetch e.department d left join fetch e.skillList WHERE e.permanent = 1")
List<Employee> getAllPermanentEmployees();
```

Notice `Employee`, `e.department`, and `e.skillList` refer to the **Java
class and its fields** (`Employee.java`, the `department` field, the
`skillList` field) — not the `employee` table or its `em_dp_id` column
directly. Hibernate translates this into the equivalent SQL joins and
column references at runtime.

Reference: https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html/ch11.html
