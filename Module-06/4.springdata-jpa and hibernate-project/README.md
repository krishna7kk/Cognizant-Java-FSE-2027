# Employee Management System — Spring Data JPA Exercises 1-10

A complete Spring Boot + Spring Data JPA project covering all 10 exercises.

---

## Tech Stack
| Dependency | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.0 |
| Spring Data JPA | (via Boot) |
| H2 (in-memory DB) | (via Boot) |
| Lombok | (via Boot) |

---

## Exercise Coverage

| Exercise | Topic | Key Files |
|---|---|---|
| 1 | Project setup, H2 config | `application.properties`, `pom.xml` |
| 2 | JPA Entities, OneToMany/ManyToOne | `Employee.java`, `Department.java` |
| 3 | Repositories, derived queries | `EmployeeRepository.java`, `DepartmentRepository.java` |
| 4 | CRUD operations, REST endpoints | `EmployeeService`, `DepartmentService`, `*Controller` |
| 5 | @Query, Named Queries | Repositories – `@Query`, `@NamedQuery` on entities |
| 6 | Pagination & Sorting | `Page`, `Pageable`, `Sort` in services + controllers |
| 7 | Entity Auditing | `@CreatedDate`, `@LastModifiedDate`, `AuditorAwareImpl` |
| 8 | Projections | `EmployeeNameEmailProjection`, `EmployeeSummaryDTO`, `DepartmentProjection` |
| 9 | DataSource configuration | `DataSourceConfig.java`, `application.properties` |
| 10 | Hibernate features, Batch | `@DynamicInsert`, `@DynamicUpdate`, `BatchService`, `BatchController` |

---

## Running the App

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

App starts at `http://localhost:8080`  
H2 Console: `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:mem:testdb` | user: `sa` | password: `password`

---

## REST API Quick Reference

### Employees
| Method | URL | Description |
|---|---|---|
| POST | `/api/employees` | Create employee |
| GET | `/api/employees` | List all |
| GET | `/api/employees/{id}` | Get by ID |
| PUT | `/api/employees/{id}` | Update |
| DELETE | `/api/employees/{id}` | Delete |
| GET | `/api/employees/search?keyword=alice` | Search |
| GET | `/api/employees/salary?min=50000&max=100000` | Salary range |
| GET | `/api/employees/paginated?page=0&size=5&sortField=name&ascending=true` | Paginated |
| GET | `/api/employees/projections/summaries` | DTO projections |
| PUT | `/api/employees/{empId}/assign/{deptId}` | Assign department |

### Departments
| Method | URL | Description |
|---|---|---|
| POST | `/api/departments` | Create |
| GET | `/api/departments` | List all |
| GET | `/api/departments/with-employees` | With employees fetched |
| GET | `/api/departments/paginated?page=0&size=5` | Paginated |
| GET | `/api/departments/projections` | Interface projections |
| GET | `/api/departments/named-search?name=eng` | Named query search |

### Batch (Exercise 10)
| Method | URL | Description |
|---|---|---|
| POST | `/api/batch/insert/{deptId}?count=50` | Batch insert employees |
| PUT | `/api/batch/deactivate/{deptId}` | Bulk deactivate |
| GET | `/api/batch/process-chunks?chunkSize=20` | Chunk processing demo |

---

## Running Tests

```bash
mvn test
```

Test classes:
- `EmployeeRepositoryTest` — Ex3, 5, 6, 8
- `DepartmentRepositoryTest` — Ex3, 5, 6, 8
- `AuditingTest` — Ex7
- `EmployeeServiceTest` — Ex4, 6
- `BatchServiceTest` — Ex10

---

## Sample POST Body

```json
POST /api/departments
{
  "name": "DevOps",
  "description": "Infrastructure and CI/CD"
}

POST /api/employees
{
  "name": "John Doe",
  "email": "john@example.com",
  "position": "DevOps Engineer",
  "salary": 85000,
  "hireDate": "2024-01-15",
  "active": true,
  "department": { "id": 1 }
}
```
