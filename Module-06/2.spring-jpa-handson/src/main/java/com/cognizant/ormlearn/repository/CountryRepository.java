package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    /**
     * Query Method (Hands On 5 - partial name search):
     * Spring Data JPA derives the query from the method name -
     * "findByNameContainingIgnoreCase" generates a LIKE '%name%' query,
     * case-insensitive.
     */
    List<Country> findByNameContainingIgnoreCase(String partialName);
}
