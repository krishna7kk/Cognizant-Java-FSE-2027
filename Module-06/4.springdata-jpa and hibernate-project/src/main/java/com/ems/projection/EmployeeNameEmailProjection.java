package com.ems.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Exercise 8: Interface-based projection for Employee
 * Only fetches name and email fields (subset of Employee).
 * Spring Data JPA generates a proxy at runtime.
 */
public interface EmployeeNameEmailProjection {

    String getName();

    String getEmail();

    /** Exercise 8: @Value with SpEL for computed/virtual field */
    @Value("#{target.name + ' <' + target.email + '>'}")
    String getNameWithEmail();
}
