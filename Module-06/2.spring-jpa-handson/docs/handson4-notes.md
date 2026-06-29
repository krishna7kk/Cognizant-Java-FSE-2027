# Hands On 4 — Difference between JPA, Hibernate, and Spring Data JPA

## Java Persistence API (JPA)

- **JSR 338** — a *specification* for persisting, reading, and managing data
  between Java objects and a relational database.
- It defines interfaces and annotations (`@Entity`, `EntityManager`, JPQL,
  etc.) but provides **no concrete implementation** itself.
- Think of it as a contract — vendors implement it however they like.

## Hibernate

- An **ORM tool that implements the JPA specification** (one of several —
  EclipseLink is another).
- Provides the actual `SessionFactory`/`EntityManager` machinery, SQL
  generation, caching, lazy loading, etc.

## Spring Data JPA

- Does **not** implement JPA itself — it sits **on top of** a JPA provider
  (typically Hibernate).
- Its job is to **eliminate boilerplate code**: instead of manually writing
  `SessionFactory` → `Session` → `Transaction` → try/catch/finally blocks,
  you declare an interface and Spring generates the implementation at
  runtime.
- Also manages transactions declaratively via `@Transactional`.

## Side-by-side comparison

**Hibernate (manual session/transaction management):**
```java
public Integer addEmployee(Employee employee) {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer employeeID = null;

    try {
        tx = session.beginTransaction();
        employeeID = (Integer) session.save(employee);
        tx.commit();
    } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
    } finally {
        session.close();
    }
    return employeeID;
}
```

**Spring Data JPA (declarative, no manual session handling):**
```java
// EmployeeRepository.java
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}

// EmployeeService.java
@Autowired
private EmployeeRepository employeeRepository;

@Transactional
public void addEmployee(Employee employee) {
    employeeRepository.save(employee);
}
```

The entire open-session / begin-transaction / try-catch-finally /
commit-or-rollback / close-session block collapses into a single
`@Transactional` method calling `.save()`. Spring proxies the method, opens a
session/transaction before the method runs, and commits (or rolls back on
exception) after it returns — exactly the behavior demonstrated by every
`CountryService` method in this project (Hands On 1, 6, 7, 8, 9).

## Summary table

| | JPA | Hibernate | Spring Data JPA |
|---|---|---|---|
| What it is | Specification (JSR 338) | ORM tool / JPA implementation | Abstraction layer over a JPA provider |
| Has implementation? | No | Yes | No (delegates to Hibernate) |
| Reduces boilerplate? | N/A | Some (vs. raw JDBC) | Significant (vs. raw Hibernate) |
| Manages transactions? | No | Manual (`Transaction` API) | Yes, declaratively (`@Transactional`) |

Reference links:
- https://dzone.com/articles/what-is-the-difference-between-hibernate-and-sprin-1
- https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html
