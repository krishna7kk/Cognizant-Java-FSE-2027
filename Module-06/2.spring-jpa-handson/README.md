# orm-learn — Spring Data JPA / Hibernate Hands-On Project

This project implements **all 9 Hands-On exercises** from the ORM / Spring Data JPA
training module:

| Hands On | Topic | Where it lives |
|---|---|---|
| 1 | Spring Data JPA quick example (Country entity, repo, service, find all) | `model/Country.java`, `repository/CountryRepository.java`, `service/CountryService.java#getAllCountries` |
| 2 | Hibernate XML Config walkthrough | conceptual — see `docs/handson2-notes.md` |
| 3 | Hibernate Annotation Config walkthrough | conceptual — see `docs/handson3-notes.md` |
| 4 | JPA vs Hibernate vs Spring Data JPA comparison | conceptual — see `docs/handson4-notes.md` |
| 5 | Populate full country list + partial-name search | `setup.sql`, `service/CountryService.java#findCountriesByPartialName` |
| 6 | Find country by code (+ `CountryNotFoundException`) | `service/CountryService.java#findCountryByCode` |
| 7 | Add a new country | `service/CountryService.java#addCountry` |
| 8 | Update a country by code | `service/CountryService.java#updateCountry` |
| 9 | Delete a country by code | `service/CountryService.java#deleteCountry` |

All test/demo calls for Hands On 1, 6, 7, 8, 9, and 5 run automatically from
`OrmLearnApplication.main()` and log their results to the console.

## Prerequisites

- JDK 17
- Maven 3.6+ (or use your IDE's built-in Maven)
- MySQL Server 8.0 **OR** just use the built-in H2 in-memory fallback (no install needed)

## Option A — Run with MySQL (matches the handout exactly)

1. Start MySQL and create the schema + table + data:
   ```
   mysql -u root -p < setup.sql
   ```
   (This script creates the `ormlearn` schema, the `country` table, and populates
   it with the full country list from Hands On 5.)

2. Confirm `src/main/resources/application.properties` has your MySQL
   username/password (defaults to `root` / `root`).

3. Build and run:
   ```
   mvn clean package
   mvn spring-boot:run
   ```
   or simply run `OrmLearnApplication` from your IDE.

## Option B — Run with H2 in-memory (zero setup, good for quick testing)

1. Open `src/main/resources/application.properties`.
2. Comment out the MySQL block, uncomment the H2 block at the bottom of the file.
3. Run:
   ```
   mvn clean package
   mvn spring-boot:run
   ```
   Hibernate will auto-create the `country` table (`ddl-auto=update`), but it
   will be **empty** — H2 starts blank each run since it's in-memory. To seed
   it, either:
   - Add the inserts from `setup.sql` to `src/main/resources/data.sql` (Spring
     Boot auto-runs this on startup), or
   - Call `countryService.addCountry(...)` a few times before running the
     other test methods.

## What you'll see in the console

Running the app triggers, in order:
1. `testGetAllCountries()` — fetches and logs every row (Hands On 1)
2. `testFindCountryByCode()` — looks up `"IN"` (should resolve to India) and
   demonstrates the `CountryNotFoundException` path with an invalid code (Hands On 6)
3. `testAddCountry()` — inserts a new country `ZX` / `Zedland` (Hands On 7)
4. `testUpdateCountry()` — renames `ZX` to `Zedland Republic` (Hands On 8)
5. `testDeleteCountry()` — removes `ZX` and confirms it's gone (Hands On 9)
6. `testFindCountriesByPartialName()` — searches for `"stan"` (matches
   Afghanistan, Pakistan, Kazakhstan, etc.) (Hands On 5 bonus)

Look for `LOGGER.info` / `LOGGER.debug` lines in the console — `ddl-auto=validate`
(MySQL mode) means the table must already exist via `setup.sql` before you run the app.

## Project Structure

```
orm-learn/
├── pom.xml
├── setup.sql                          <- run this against MySQL first
├── README.md
├── docs/
│   ├── handson2-notes.md              <- Hibernate XML config walkthrough notes
│   ├── handson3-notes.md              <- Hibernate annotation config walkthrough notes
│   └── handson4-notes.md              <- JPA vs Hibernate vs Spring Data JPA notes
└── src/
    ├── main/
    │   ├── java/com/cognizant/ormlearn/
    │   │   ├── OrmLearnApplication.java
    │   │   ├── model/Country.java
    │   │   ├── repository/CountryRepository.java
    │   │   ├── service/CountryService.java
    │   │   └── service/exception/CountryNotFoundException.java
    │   └── resources/application.properties
    └── test/
        └── java/com/cognizant/ormlearn/OrmLearnApplicationTests.java
```

## Note on Java Persistence imports

This project intentionally targets **Spring Boot 2.7.x**, which uses
`javax.persistence.*` annotations — matching the handout exactly
(`@Entity`, `@Table`, `@Id`, `@Column` from `javax.persistence`). Spring Boot 3.x
moved to `jakarta.persistence.*`; if you upgrade the parent POM to Spring Boot 3,
update the imports in `Country.java` accordingly.
