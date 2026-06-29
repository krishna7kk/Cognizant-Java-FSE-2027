package com.ems.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Exercise 2 : JPA Entity - Employee with Department ManyToOne
 * Exercise 7 : Auditing annotations
 * Exercise 10: Hibernate-specific @DynamicInsert, @DynamicUpdate, @BatchSize
 */
@Entity
@Table(name = "employees", indexes = {
    @Index(name = "idx_employee_email", columnList = "email", unique = true),
    @Index(name = "idx_employee_dept",  columnList = "department_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Exercise 10: Hibernate-specific annotations
@DynamicInsert   // only include non-null columns on INSERT
@DynamicUpdate   // only include changed columns on UPDATE
// Exercise 5: Named Queries on Employee
@NamedQueries({
    @NamedQuery(
        name  = "Employee.findByDepartmentName",
        query = "SELECT e FROM Employee e WHERE e.department.name = :deptName"
    ),
    @NamedQuery(
        name  = "Employee.findActiveEmployees",
        query = "SELECT e FROM Employee e WHERE e.active = true ORDER BY e.name ASC"
    )
})
public class Employee {

    // Exercise 2
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 50)
    private String position;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // Exercise 2: ManyToOne -> Department (Many employees belong to one Department)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Department department;

    // Exercise 7: Audit fields
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(updatable = false, length = 50)
    private String createdBy;

    @LastModifiedBy
    @Column(length = 50)
    private String lastModifiedBy;

    // Exercise 10: @BatchSize - controls batch fetching of collections
    // (applied at collection level in Department, but shown here as reference)
}
