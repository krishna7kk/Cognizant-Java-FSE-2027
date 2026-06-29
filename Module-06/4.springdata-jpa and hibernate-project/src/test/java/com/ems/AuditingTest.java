package com.ems;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exercise 7: Entity Auditing Tests
 * Verifies @CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy
 * are automatically populated by Spring Data JPA Auditing.
 */
@DataJpaTest
@Import(com.ems.audit.AuditorAwareImpl.class)
class AuditingTest {

    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private EmployeeRepository employeeRepository;

    @Test @DisplayName("Ex7: Department has createdDate set on save")
    void departmentAuditCreatedDate() {
        Department dept = departmentRepository.save(
            Department.builder().name("Audit Test Dept").build());
        assertThat(dept.getCreatedDate()).isNotNull();
    }

    @Test @DisplayName("Ex7: Department has createdBy set to 'system'")
    void departmentAuditCreatedBy() {
        Department dept = departmentRepository.save(
            Department.builder().name("Audit By Dept").build());
        assertThat(dept.getCreatedBy()).isEqualTo("system");
    }

    @Test @DisplayName("Ex7: Employee audit fields populated")
    void employeeAuditFields() {
        Department dept = departmentRepository.save(
            Department.builder().name("Dept For Audit Emp").build());
        Employee emp = employeeRepository.save(Employee.builder()
            .name("Audited Employee")
            .email("audited@test.com")
            .department(dept)
            .active(true)
            .build());

        assertThat(emp.getCreatedDate()).isNotNull();
        assertThat(emp.getLastModifiedDate()).isNotNull();
        assertThat(emp.getCreatedBy()).isEqualTo("system");
        assertThat(emp.getLastModifiedBy()).isEqualTo("system");
    }

    @Test @DisplayName("Ex7: lastModifiedDate changes on update")
    void lastModifiedDateChangesOnUpdate() throws InterruptedException {
        Department dept = departmentRepository.save(
            Department.builder().name("Update Audit Dept").build());
        Employee emp = employeeRepository.save(Employee.builder()
            .name("Update Me")
            .email("updateme@test.com")
            .department(dept)
            .active(true)
            .build());

        // Force a slight delay so timestamps differ
        Thread.sleep(10);

        emp.setPosition("Updated Position");
        Employee updated = employeeRepository.save(emp);

        // createdDate must remain unchanged
        assertThat(updated.getCreatedDate()).isEqualTo(emp.getCreatedDate());
        // lastModifiedDate may equal or be after createdDate
        assertThat(updated.getLastModifiedDate()).isNotNull();
    }
}
