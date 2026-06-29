package com.ems;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.projection.EmployeeNameEmailProjection;
import com.ems.projection.EmployeeSummaryDTO;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests covering:
 * Exercise 3: Repository CRUD
 * Exercise 5: Derived queries + @Query
 * Exercise 6: Pagination + Sorting
 * Exercise 8: Projections
 */
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private DepartmentRepository departmentRepository;

    private Department engineering;
    private Department hr;

    @BeforeEach
    void setUp() {
        engineering = departmentRepository.save(
            Department.builder().name("Engineering").description("Dev team").build());
        hr = departmentRepository.save(
            Department.builder().name("Human Resources").description("HR team").build());

        employeeRepository.save(Employee.builder()
            .name("Alice").email("alice@test.com").position("Engineer")
            .salary(new BigDecimal("90000")).hireDate(LocalDate.now())
            .department(engineering).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("Bob").email("bob@test.com").position("Manager")
            .salary(new BigDecimal("120000")).hireDate(LocalDate.now())
            .department(engineering).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("Carol").email("carol@test.com").position("Recruiter")
            .salary(new BigDecimal("60000")).hireDate(LocalDate.now())
            .department(hr).active(false).build());
    }

    // ---- Exercise 3: Basic CRUD ----

    @Test @DisplayName("Ex3: Save and find employee by ID")
    void saveAndFindById() {
        List<Employee> all = employeeRepository.findAll();
        assertThat(all).hasSize(3);
        Optional<Employee> found = employeeRepository.findById(all.get(0).getId());
        assertThat(found).isPresent();
    }

    @Test @DisplayName("Ex3: Delete employee")
    void deleteEmployee() {
        Employee emp = employeeRepository.findAll().get(0);
        employeeRepository.delete(emp);
        assertThat(employeeRepository.count()).isEqualTo(2);
    }

    @Test @DisplayName("Ex3: Update employee")
    void updateEmployee() {
        Employee emp = employeeRepository.findByEmail("alice@test.com").orElseThrow();
        emp.setPosition("Senior Engineer");
        Employee saved = employeeRepository.save(emp);
        assertThat(saved.getPosition()).isEqualTo("Senior Engineer");
    }

    // ---- Exercise 5: Derived Queries ----

    @Test @DisplayName("Ex5: findByEmail")
    void findByEmail() {
        Optional<Employee> emp = employeeRepository.findByEmail("bob@test.com");
        assertThat(emp).isPresent();
        assertThat(emp.get().getName()).isEqualTo("Bob");
    }

    @Test @DisplayName("Ex5: findByNameContainingIgnoreCase")
    void findByNameContaining() {
        List<Employee> result = employeeRepository.findByNameContainingIgnoreCase("al");
        assertThat(result).hasSize(1).extracting(Employee::getName).contains("Alice");
    }

    @Test @DisplayName("Ex5: findByDepartmentId")
    void findByDepartmentId() {
        List<Employee> result = employeeRepository.findByDepartmentId(engineering.getId());
        assertThat(result).hasSize(2);
    }

    @Test @DisplayName("Ex5: findBySalaryGreaterThan")
    void findBySalaryGreaterThan() {
        List<Employee> result = employeeRepository.findBySalaryGreaterThan(new BigDecimal("100000"));
        assertThat(result).hasSize(1).extracting(Employee::getName).contains("Bob");
    }

    @Test @DisplayName("Ex5: findBySalaryBetween")
    void findBySalaryBetween() {
        List<Employee> result = employeeRepository.findBySalaryBetween(
            new BigDecimal("50000"), new BigDecimal("100000"));
        assertThat(result).hasSize(2);
    }

    @Test @DisplayName("Ex5: existsByEmail")
    void existsByEmail() {
        assertThat(employeeRepository.existsByEmail("alice@test.com")).isTrue();
        assertThat(employeeRepository.existsByEmail("notexist@test.com")).isFalse();
    }

    @Test @DisplayName("Ex5: searchByKeyword @Query")
    void searchByKeyword() {
        List<Employee> result = employeeRepository.searchByKeyword("bob");
        assertThat(result).hasSize(1).extracting(Employee::getName).contains("Bob");
    }

    @Test @DisplayName("Ex5: findAllWithDepartment JOIN FETCH")
    void findAllWithDepartment() {
        List<Employee> result = employeeRepository.findAllWithDepartment();
        assertThat(result).hasSize(3);
        result.forEach(e -> assertThat(e.getDepartment()).isNotNull());
    }

    @Test @DisplayName("Ex5: findActiveNative native query")
    void findActiveNative() {
        List<Employee> result = employeeRepository.findAllActiveNative();
        assertThat(result).hasSize(2);
    }

    // ---- Exercise 6: Pagination + Sorting ----

    @Test @DisplayName("Ex6: Paginated findAll - page 0 size 2")
    void paginatedFindAll() {
        Page<Employee> page = employeeRepository.findAll(PageRequest.of(0, 2));
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }

    @Test @DisplayName("Ex6: Sorted findAll - sort by salary DESC")
    void sortedFindAll() {
        List<Employee> sorted = employeeRepository.findAll(Sort.by("salary").descending());
        assertThat(sorted.get(0).getName()).isEqualTo("Bob");
    }

    @Test @DisplayName("Ex6: Paginated by department")
    void paginatedByDepartment() {
        Page<Employee> page = employeeRepository.findByDepartmentId(
            engineering.getId(), PageRequest.of(0, 10));
        assertThat(page.getContent()).hasSize(2);
    }

    @Test @DisplayName("Ex6: Paginated name search")
    void paginatedNameSearch() {
        Page<Employee> page = employeeRepository.findByNameContainingIgnoreCase(
            "a", PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(1);
    }

    // ---- Exercise 8: Projections ----

    @Test @DisplayName("Ex8: Interface-based projection")
    void interfaceProjection() {
        List<EmployeeNameEmailProjection> projections =
            employeeRepository.findProjectedByDepartmentId(engineering.getId());
        assertThat(projections).hasSize(2);
        projections.forEach(p -> {
            assertThat(p.getName()).isNotBlank();
            assertThat(p.getEmail()).isNotBlank();
            assertThat(p.getNameWithEmail()).contains("<").contains(">");
        });
    }

    @Test @DisplayName("Ex8: Class-based DTO projection")
    void dtoProjection() {
        List<EmployeeSummaryDTO> summaries = employeeRepository.findActiveEmployeeSummaries();
        assertThat(summaries).hasSize(2);
        summaries.forEach(s -> assertThat(s.getDepartmentName()).isNotBlank());
    }
}
