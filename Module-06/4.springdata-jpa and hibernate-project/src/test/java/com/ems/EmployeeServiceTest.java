package com.ems;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Exercise 4: Integration tests for EmployeeService CRUD operations.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EmployeeServiceTest {

    @Autowired private EmployeeService employeeService;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private DepartmentRepository departmentRepository;

    private Department dept;

    @BeforeEach
    void setUp() {
        // DataInitializer runs automatically; grab or create a dept
        dept = departmentRepository.findByName("Engineering")
            .orElseGet(() -> departmentRepository.save(
                Department.builder().name("Engineering").build()));
    }

    @Test @DisplayName("Ex4: Create employee succeeds")
    void createEmployee() {
        Employee emp = Employee.builder()
            .name("Test User").email("testuser@example.com")
            .position("QA").salary(new BigDecimal("55000"))
            .department(dept).active(true).build();
        Employee saved = employeeService.createEmployee(emp);
        assertThat(saved.getId()).isNotNull();
    }

    @Test @DisplayName("Ex4: Duplicate email throws exception")
    void duplicateEmailThrows() {
        // alice@example.com is seeded by DataInitializer
        Employee dup = Employee.builder()
            .name("Dup").email("alice@example.com")
            .department(dept).active(true).build();
        assertThatThrownBy(() -> employeeService.createEmployee(dup))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email already in use");
    }

    @Test @DisplayName("Ex4: Update employee position")
    void updateEmployee() {
        Employee alice = employeeRepository.findByEmail("alice@example.com").orElseThrow();
        alice.setPosition("Principal Engineer");
        Employee updated = employeeService.updateEmployee(alice.getId(), alice);
        assertThat(updated.getPosition()).isEqualTo("Principal Engineer");
    }

    @Test @DisplayName("Ex4: Delete employee")
    void deleteEmployee() {
        long before = employeeRepository.count();
        Employee emp = employeeRepository.findByEmail("jack@example.com").orElseThrow();
        employeeService.deleteEmployee(emp.getId());
        assertThat(employeeRepository.count()).isEqualTo(before - 1);
    }

    @Test @DisplayName("Ex6: Paginated employees returns correct page size")
    void paginatedEmployees() {
        Page<Employee> page = employeeService.findAllPaginated(0, 3, "name", true);
        assertThat(page.getContent()).hasSize(3);
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(10);
    }

    @Test @DisplayName("Ex6: Search paginated")
    void searchPaginated() {
        Page<Employee> page = employeeService.searchPaginated("alice", 0, 10);
        assertThat(page.getTotalElements()).isEqualTo(1);
    }
}
