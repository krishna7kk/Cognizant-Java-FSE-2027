# Hands On 3 — Hibernate Annotation Config Implementation Walkthrough

Reference: https://www.tutorialspoint.com/hibernate/hibernate_annotations.htm

## Object-to-relational mapping via annotations

Instead of a separate `.hbm.xml` mapping file (Hands On 2), the mapping is
declared directly on the `Employee` persistence class using annotations:

```java
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "salary")
    private int salary;

    // getters / setters
}
```

## Annotation roles

| Annotation | Purpose |
|---|---|
| `@Entity` | Marks the class as a persistent entity managed by Hibernate. |
| `@Table` | Maps the class to a specific database table (optional if names match). |
| `@Id` | Designates the primary key field. |
| `@GeneratedValue` | Tells Hibernate the primary key value should be generated automatically (e.g. auto-increment / identity / sequence), rather than supplied by the application. |
| `@Column` | Maps a field to a specific column (optional if the field name matches the column name). |

This is the same pattern used later by the `Country` entity in Hands On 1 —
annotation config is what Spring Data JPA builds on top of.

## Hibernate Configuration (`hibernate.cfg.xml`)

Even with annotation-based mapping, the **connection and behavior settings**
still live in `hibernate.cfg.xml` (or, in the Spring Boot world, in
`application.properties`):

| Setting | Purpose |
|---|---|
| **Dialect** | Tells Hibernate which SQL variant to generate (e.g. `MySQL5Dialect`, `H2Dialect`) so generated SQL is compatible with the target database. |
| **Driver** | The JDBC driver class used to connect (e.g. `com.mysql.cj.jdbc.Driver`). |
| **Connection URL** | The JDBC URL identifying the database server, port, and schema. |
| **Username / Password** | Credentials used to authenticate the JDBC connection. |

In this project, these same four settings appear as:
```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/ormlearn
spring.datasource.username=root
spring.datasource.password=root
```

Spring Boot auto-configures the `SessionFactory`/`EntityManager` from these
properties, so you never write `hibernate.cfg.xml` by hand in a Spring Data
JPA project — that's one of the boilerplate-reduction benefits covered in
Hands On 4.
