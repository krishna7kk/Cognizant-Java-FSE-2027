package com.ems.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 2: JPA Entity with @Entity, @Table, @Id, @GeneratedValue
 * Exercise 7: Auditing fields - @CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy
 * Exercise 10: Hibernate-specific @NamedQuery
 */
@Entity
@Table(name = "departments")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Exercise 5: Named Queries
@NamedQueries({
    @NamedQuery(
        name = "Department.findByNameContaining",
        query = "SELECT d FROM Department d WHERE d.name LIKE %:name%"
    ),
    @NamedQuery(
        name = "Department.countEmployees",
        query = "SELECT COUNT(e) FROM Employee e WHERE e.department.id = :deptId"
    )
})
public class Department {

    // Exercise 2: @Id + @GeneratedValue
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    // Exercise 2: One-to-Many relationship (Department -> Employees)
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();

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
}
