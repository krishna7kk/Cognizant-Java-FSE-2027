package com.ems;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.projection.DepartmentProjection;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests covering:
 * Exercise 3: DepartmentRepository CRUD
 * Exercise 5: Department queries
 * Exercise 6: Pagination
 * Exercise 8: DepartmentProjection
 */
@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        Department eng = departmentRepository.save(
            Department.builder().name("Engineering").description("Dev").build());
        departmentRepository.save(
            Department.builder().name("Finance").description("Fin").build());
        departmentRepository.save(
            Department.builder().name("Marketing").description("Mkt").build());

        employeeRepository.save(Employee.builder()
            .name("Dev1").email("dev1@test.com")
            .department(eng).active(true).build());
        employeeRepository.save(Employee.builder()
            .name("Dev2").email("dev2@test.com")
            .department(eng).active(true).build());
    }

    // Exercise 3: CRUD

    @Test @DisplayName("Ex3: Save and count departments")
    void saveAndCount() {
        assertThat(departmentRepository.count()).isEqualTo(3);
    }

    @Test @DisplayName("Ex3: FindById")
    void findById() {
        Department dept = departmentRepository.findByName("Finance").orElseThrow();
        Optional<Department> found = departmentRepository.findById(dept.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Finance");
    }

    @Test @DisplayName("Ex3: Update department name")
    void updateDepartment() {
        Department dept = departmentRepository.findByName("Marketing").orElseThrow();
        dept.setDescription("Marketing & Growth");
        departmentRepository.save(dept);
        Department updated = departmentRepository.findByName("Marketing").orElseThrow();
        assertThat(updated.getDescription()).isEqualTo("Marketing & Growth");
    }

    @Test @DisplayName("Ex3: Delete department")
    void deleteDepartment() {
        Department dept = departmentRepository.findByName("Finance").orElseThrow();
        departmentRepository.delete(dept);
        assertThat(departmentRepository.count()).isEqualTo(2);
    }

    // Exercise 5: Queries

    @Test @DisplayName("Ex5: findByName")
    void findByName() {
        Optional<Department> dept = departmentRepository.findByName("Engineering");
        assertThat(dept).isPresent();
    }

    @Test @DisplayName("Ex5: findByNameContaining")
    void findByNameContaining() {
        List<Department> result = departmentRepository.findByNameContainingIgnoreCase("ing");
        assertThat(result).hasSizeGreaterThanOrEqualTo(2); // Engineering + Marketing
    }

    @Test @DisplayName("Ex5: existsByName")
    void existsByName() {
        assertThat(departmentRepository.existsByName("Engineering")).isTrue();
        assertThat(departmentRepository.existsByName("NonExistent")).isFalse();
    }

    @Test @DisplayName("Ex5: findAllWithEmployees JOIN FETCH")
    void findAllWithEmployees() {
        List<Department> result = departmentRepository.findAllWithEmployees();
        assertThat(result).hasSize(3);
    }

    @Test @DisplayName("Ex5: findDepartmentEmployeeCount")
    void findDepartmentEmployeeCount() {
        List<Object[]> counts = departmentRepository.findDepartmentEmployeeCount();
        assertThat(counts).isNotEmpty();
    }

    // Exercise 6: Pagination

    @Test @DisplayName("Ex6: Paginated findAll")
    void paginatedFindAll() {
        Page<Department> page = departmentRepository.findAll(PageRequest.of(0, 2));
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test @DisplayName("Ex6: Paginated search by name")
    void paginatedSearch() {
        Page<Department> page = departmentRepository
            .findByNameContainingIgnoreCase("e", PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(1);
    }

    // Exercise 8: Projections

    @Test @DisplayName("Ex8: DepartmentProjection interface")
    void departmentProjection() {
        List<DepartmentProjection> projections = departmentRepository.findProjectedBy();
        assertThat(projections).hasSize(3);
        projections.forEach(p -> {
            assertThat(p.getId()).isNotNull();
            assertThat(p.getName()).isNotBlank();
            assertThat(p.getDescriptionOrDefault()).isNotBlank();
        });
    }
}
