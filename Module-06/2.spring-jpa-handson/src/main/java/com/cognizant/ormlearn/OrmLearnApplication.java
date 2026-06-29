package com.cognizant.ormlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        LOGGER.info("Inside main");

        countryService = context.getBean(CountryService.class);

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
}
