package com.ems.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Exercise 8: Interface-based projection for Department.
 * Fetches only id and name.
 */
public interface DepartmentProjection {

    Long getId();

    String getName();

    /** SpEL @Value computed field - description capped or default */
    @Value("#{target.description != null ? target.description : 'No description'}")
    String getDescriptionOrDefault();
}
