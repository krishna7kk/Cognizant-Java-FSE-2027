package com.ems.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exercise 8: Class-based (DTO) projection for Employee summary.
 * Used with constructor expressions in @Query.
 * "SELECT new com.ems.projection.EmployeeSummaryDTO(...) FROM Employee e ..."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSummaryDTO {

    private Long id;
    private String name;
    private String email;
    private String position;
    private String departmentName;
}
