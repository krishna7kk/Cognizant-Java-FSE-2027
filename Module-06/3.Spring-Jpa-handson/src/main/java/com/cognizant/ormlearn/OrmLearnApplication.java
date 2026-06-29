package com.cognizant.ormlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Attempt;
import com.cognizant.ormlearn.model.AttemptOption;
import com.cognizant.ormlearn.model.AttemptQuestion;
import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.service.AttemptService;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;
    private static EmployeeService employeeService;
    private static AttemptService attemptService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        LOGGER.info("Inside main");

        countryService = context.getBean(CountryService.class);
        employeeService = context.getBean(EmployeeService.class);
        attemptService = context.getBean(AttemptService.class);

        // ---- Hands On 1: get all countries ----
        testGetAllCountries();

        // ---- Hands On 6: find a country based on country code ----
        testFindCountryByCode();

        // ---- Hands On 7: add a new country ----
        testAddCountry();

        // ---- Hands On 8: update a country based on code ----
        testUpdateCountry();

        // ---- Hands On 9: delete a country based on code ----
        testDeleteCountry();

        // ---- Hands On 5 (bonus): partial name search ----
        testFindCountriesByPartialName();

        // =====================================================
        // Module 3 - Spring Data JPA: HQL, JPQL, Native & Criteria
        // =====================================================

        // ---- Hands On 2: get all permanent employees using HQL ----
        testGetAllPermanentEmployees();

        // ---- Hands On 3: fetch quiz attempt details using HQL ----
        testGetAttemptDetail();

        // ---- Hands On 4: get average salary using HQL ----
        testGetAverageSalaryByDepartment();

        // ---- Hands On 5: get all employees using Native Query ----
        testGetAllEmployeesNative();
    }

    /**
     * Hands On 1: retrieve and log all countries.
     */
    private static void testGetAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries={}", countries);
        LOGGER.info("Total countries found: {}", countries.size());
        LOGGER.info("End");
    }

    /**
     * Hands On 6: find a country based on country code and verify the name.
     */
    private static void testFindCountryByCode() {
        LOGGER.info("Start");
        try {
            Country country = countryService.findCountryByCode("IN");
            LOGGER.debug("Country:{}", country);

            if ("India".equals(country.getName())) {
                LOGGER.info("PASS: country code IN resolved to India");
            } else {
                LOGGER.warn("FAIL: expected India but got {}", country.getName());
            }
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found", e);
        }

        // also demonstrate the not-found path
        try {
            countryService.findCountryByCode("ZZ");
        } catch (CountryNotFoundException e) {
            LOGGER.info("Expected exception caught for invalid code ZZ: {}", e.getMessage());
        }

        LOGGER.info("End");
    }

    /**
     * Hands On 7: add a new country and verify it was added.
     */
    private static void testAddCountry() {
        LOGGER.info("Start");

        String newCode = "ZX";
        String newName = "Zedland";

        Country country = new Country(newCode, newName);
        countryService.addCountry(country);

        try {
            Country added = countryService.findCountryByCode(newCode);
            LOGGER.debug("Added country verified as: {}", added);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country was not added successfully", e);
        }

        LOGGER.info("End");
    }

    /**
     * Hands On 8: update the country added above and verify the name changed.
     */
    private static void testUpdateCountry() {
        LOGGER.info("Start");

        String code = "ZX";
        String updatedName = "Zedland Republic";

        try {
            countryService.updateCountry(code, updatedName);
            Country updated = countryService.findCountryByCode(code);
            LOGGER.debug("Country after update: {}", updated);

            if (updatedName.equals(updated.getName())) {
                LOGGER.info("PASS: country name updated successfully");
            } else {
                LOGGER.warn("FAIL: name not updated as expected");
            }
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found for update", e);
        }

        LOGGER.info("End");
    }

    /**
     * Hands On 9: delete the country added/updated above and verify deletion.
     */
    private static void testDeleteCountry() {
        LOGGER.info("Start");

        String code = "ZX";
        countryService.deleteCountry(code);

        try {
            countryService.findCountryByCode(code);
            LOGGER.warn("FAIL: country {} still exists after delete", code);
        } catch (CountryNotFoundException e) {
            LOGGER.info("PASS: country {} successfully deleted", code);
        }

        LOGGER.info("End");
    }

    /**
     * Hands On 5 (bonus): find countries matching a partial name.
     */
    private static void testFindCountriesByPartialName() {
        LOGGER.info("Start");
        List<Country> matches = countryService.findCountriesByPartialName("stan");
        LOGGER.debug("Countries matching 'stan': {}", matches);
        LOGGER.info("Found {} countries matching 'stan'", matches.size());
        LOGGER.info("End");
    }

    /**
     * Hands On 2: get all permanent employees using HQL, including each
     * employee's department and skill list (populated via 'left join fetch'
     * in EmployeeRepository.getAllPermanentEmployees()).
     */
    public static void testGetAllPermanentEmployees() {
        LOGGER.info("Start");
        List<Employee> employees = employeeService.getAllPermanentEmployees();
        LOGGER.debug("Permanent Employees:{}", employees);
        employees.forEach(e -> LOGGER.debug("Skills:{}", e.getSkillList()));
        LOGGER.info("End");
    }

    /**
     * Hands On 3: fetch quiz attempt details using HQL and print the
     * questions, options, scores, and the user's selected answer for each
     * question - matching the sample output format in the handout:
     *
     *   What is the extension of the hyper text markup language file?
     *    1) .xhtm       0.0     false
     *    2) .ht         0.0     false
     *    3) .html       1.0     true
     *    4) .htmx       0.0     false
     */
    private static void testGetAttemptDetail() {
        LOGGER.info("Start");

        int userId = 1;
        int attemptId = 1;

        Attempt attempt = attemptService.getAttempt(userId, attemptId);

        if (attempt == null) {
            LOGGER.warn("No attempt found for userId={}, attemptId={}", userId, attemptId);
            LOGGER.info("End");
            return;
        }

        LOGGER.info("User: {}", attempt.getUser().getUsername());
        LOGGER.info("Attempted Date: {}", attempt.getAttemptedDate());

        for (AttemptQuestion aq : attempt.getAttemptQuestionList()) {
            System.out.println();
            System.out.println(aq.getQuestion().getText());

            int optionNumber = 1;
            for (AttemptOption ao : aq.getAttemptOptionList()) {
                System.out.printf(" %d) %-12s %-6s %s%n",
                    optionNumber++,
                    ao.getOption().getText(),
                    ao.getOption().getScore(),
                    ao.isSelected());
            }
        }

        LOGGER.info("End");
    }

    /**
     * Hands On 4: get average salary across employees in a given department
     * using an HQL aggregate function with a named parameter.
     */
    private static void testGetAverageSalaryByDepartment() {
        LOGGER.info("Start");

        int departmentId = 1;
        double averageSalary = employeeService.getAverageSalaryByDepartment(departmentId);
        LOGGER.info("Average salary for department id {}: {}", departmentId, averageSalary);

        LOGGER.info("End");
    }

    /**
     * Hands On 5: get all employees using a Native Query (raw SQL) instead
     * of HQL/JPQL.
     */
    private static void testGetAllEmployeesNative() {
        LOGGER.info("Start");

        List<Employee> employees = employeeService.getAllEmployeesNative();
        LOGGER.debug("Employees (native query):{}", employees);
        LOGGER.info("Total employees found (native query): {}", employees.size());

        LOGGER.info("End");
    }
}
