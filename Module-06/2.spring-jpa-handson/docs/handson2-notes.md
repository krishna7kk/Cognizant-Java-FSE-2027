# Hands On 2 — Hibernate XML Config Implementation Walkthrough

Reference: https://www.tutorialspoint.com/hibernate/hibernate_examples.htm

## Object-to-relational mapping via XML

In pure Hibernate (no Spring Data JPA), a plain Java class (e.g. `Employee`) has
**no annotations at all**. The mapping between the class's fields and the
database table's columns lives entirely in a separate `Employee.hbm.xml` file:

```xml
<hibernate-mapping>
    <class name="Employee" table="EMPLOYEE">
        <id name="id" type="int" column="id">
            <generator class="native"/>
        </id>
        <property name="firstName" column="first_name" type="string"/>
        <property name="lastName" column="last_name" type="string"/>
        <property name="salary" column="salary" type="int"/>
    </class>
</hibernate-mapping>
```

This is then registered in the main `hibernate.cfg.xml` so Hibernate knows to
load it at startup.

## End-to-end operation lifecycle

| Concept | Role |
|---|---|
| `SessionFactory` | Heavyweight, thread-safe, built once per application (or per database) from `hibernate.cfg.xml`. Produces `Session` objects. |
| `Session` | Lightweight, single-threaded unit of work. Wraps a JDBC connection. **Not** thread-safe — create one per operation/request. |
| `Transaction` | Represents a unit of work to commit or roll back atomically. |
| `beginTransaction()` | Starts a new transaction on the current session. |
| `commit()` | Flushes pending changes to the database and ends the transaction successfully. |
| `rollback()` | Reverts all changes made in the current transaction (called in `catch` blocks). |
| `session.save()` | Inserts a new persistent object; returns the generated identifier. |
| `session.createQuery().list()` | Executes an HQL (Hibernate Query Language) query and returns matching results as a `List`. |
| `session.get()` | Retrieves an object by its primary key; returns `null` if not found. |
| `session.delete()` | Deletes a persistent object from the database. |

## Typical flow (the pattern Spring Data JPA later automates away)

```java
Session session = factory.openSession();
Transaction tx = null;
try {
    tx = session.beginTransaction();
    // ... session.save() / get() / createQuery().list() / delete() ...
    tx.commit();
} catch (HibernateException e) {
    if (tx != null) tx.rollback();
    e.printStackTrace();
} finally {
    session.close();
}
```

This open → begin → operate → commit/rollback → close pattern is exactly what
Spring's `@Transactional` annotation handles automatically in the Spring Data
JPA version of the same logic (see Hands On 4 notes).
