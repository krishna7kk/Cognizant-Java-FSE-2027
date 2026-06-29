# MODULE5 - Spring Core Maven Exercises

## Project Structure

```
MODULE5/
├── LibraryManagement/          ← Exercises 1–8 (Spring Core + XML Config)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/library/
│       │   ├── LibraryManagementApplication.java   (Main class)
│       │   ├── service/BookService.java
│       │   ├── repository/BookRepository.java
│       │   └── aspect/LoggingAspect.java
│       └── resources/
│           └── applicationContext.xml
│
└── LibraryManagementBoot/      ← Exercise 9 (Spring Boot)
    ├── pom.xml
    └── src/main/
        ├── java/com/library/
        │   ├── LibraryManagementBootApplication.java
        │   ├── entity/Book.java
        │   ├── repository/BookRepository.java
        │   └── controller/BookController.java
        └── resources/
            └── application.properties
```

---

## Exercise Descriptions & How to Run

---

### Exercise 1: Configuring a Basic Spring Application
**Files:** `pom.xml`, `applicationContext.xml`, `BookService.java`, `BookRepository.java`, `LibraryManagementApplication.java`

**Run:**
```bash
cd LibraryManagement
mvn compile exec:java -Dexec.mainClass="com.library.LibraryManagementApplication"
```

**Expected Output:**
```
Spring Application Context loaded successfully.
BookRepository: Initialized with 3 books.
BookService: Initialized via Constructor Injection.
```

---

### Exercise 2: Implementing Dependency Injection
**Files:** `applicationContext.xml` (property wiring), `BookService.java` (setter method)

The `applicationContext.xml` wires `BookRepository` into `BookService` via:
```xml
<bean id="bookService" class="com.library.service.BookService">
    <property name="bookRepository" ref="bookRepository" />
</bean>
```

**Expected Output:**
```
BookService: BookRepository set via Setter Injection.
```

---

### Exercise 3: Implementing Logging with Spring AOP
**Files:** `LoggingAspect.java`, `applicationContext.xml` (`<aop:aspectj-autoproxy/>`)

The `LoggingAspect` uses `@Around` advice to measure method execution time.

**Expected Output:**
```
[AOP - Around] Method: com.library.service.BookService.getAllBooks executed in X ms
```

---

### Exercise 4: Creating and Configuring a Maven Project
**File:** `pom.xml`

The `pom.xml` includes:
- `spring-context`, `spring-aop`, `spring-webmvc` dependencies
- Maven Compiler Plugin configured for Java 1.8

---

### Exercise 5: Configuring the Spring IoC Container
**File:** `applicationContext.xml`

Beans are defined:
```xml
<bean id="bookRepository" class="com.library.repository.BookRepository" />
<bean id="bookService" class="com.library.service.BookService">
    <property name="bookRepository" ref="bookRepository" />
</bean>
```

---

### Exercise 6: Configuring Beans with Annotations
**Files:** `BookService.java` (`@Service`), `BookRepository.java` (`@Repository`), `applicationContext.xml` (component scan)

Component scanning is enabled:
```xml
<context:component-scan base-package="com.library" />
```

---

### Exercise 7: Constructor and Setter Injection
**Files:** `BookService.java`, `applicationContext.xml`

```xml
<bean id="bookService" class="com.library.service.BookService">
    <constructor-arg ref="bookRepository" />   <!-- Constructor Injection -->
    <property name="bookRepository" ref="bookRepository" /> <!-- Setter Injection -->
</bean>
```

**Expected Output:**
```
BookService: Initialized via Constructor Injection.
BookService: BookRepository set via Setter Injection.
```

---

### Exercise 8: Implementing Basic AOP with Spring
**File:** `LoggingAspect.java`

Four advice types are implemented:
- `@Before` — logs before method execution
- `@After` — logs after method execution
- `@Around` — measures execution time
- `@AfterReturning` — logs return values

**Expected Output:**
```
[AOP - Before] Entering method: com.library.service.BookService.getAllBooks
[AOP - After]  Exiting method:  com.library.service.BookService.getAllBooks
[AOP - AfterReturning] Method: getAllBooks returned: [...]
[AOP - Around] Method: ... executed in X ms
```

---

### Exercise 9: Creating a Spring Boot Application
**Project:** `LibraryManagementBoot/`

**Run:**
```bash
cd LibraryManagementBoot
mvn spring-boot:run
```

**Expected Startup Output:**
```
Seeded 4 books into the database.
Application is running!
REST API: http://localhost:8080/api/books
H2 Console: http://localhost:8080/h2-console
```

**REST Endpoints:**

| Method | URL | Description |
|--------|-----|-------------|
| GET    | `/api/books` | Get all books |
| GET    | `/api/books/{id}` | Get book by ID |
| POST   | `/api/books` | Create a new book |
| PUT    | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book |
| GET    | `/api/books/search?keyword=xxx` | Search by title |

**Test with curl:**
```bash
# Get all books
curl http://localhost:8080/api/books

# Create a book
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Spring in Action","author":"Craig Walls","isbn":"ISBN-001"}'

# Get book by ID
curl http://localhost:8080/api/books/1

# Update a book
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Title","author":"Author","isbn":"ISBN-001","available":false}'

# Delete a book
curl -X DELETE http://localhost:8080/api/books/1

# Search books
curl "http://localhost:8080/api/books/search?keyword=spring"
```

---

## Running Exercises 1–8 Together

```bash
cd LibraryManagement
mvn clean compile
mvn exec:java -Dexec.mainClass="com.library.LibraryManagementApplication"
```

**Full Expected Console Output:**
```
==========================================================
  Library Management Application - Spring Core Exercises  
==========================================================

--- Loading Spring Application Context ---
BookRepository: Initialized with 3 books.
BookService: Initialized via Constructor Injection.
BookService: BookRepository set via Setter Injection.
Spring Application Context loaded successfully.

--- Retrieving BookService bean from context ---
BookService bean retrieved successfully.

--- Exercise 3 & 8: AOP Logging Demo ---

[Test 1] Fetching all books:
[AOP - Before] Entering method: com.library.service.BookService.getAllBooks
BookService: Calling getAllBooks().
BookRepository: Fetching all books from repository.
[AOP - After]  Exiting method: com.library.service.BookService.getAllBooks
[AOP - AfterReturning] Method: getAllBooks returned: [...]
[AOP - Around] Method: BookService.getAllBooks executed in X ms
Books retrieved: [The Spring Framework in Action, Clean Code ..., Design Patterns ...]

[Test 2] Adding a new book:
...

==========================================================
  All Exercises Completed Successfully!                  
==========================================================
```
