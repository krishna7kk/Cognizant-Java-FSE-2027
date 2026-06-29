package com.ems.service;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.projection.EmployeeNameEmailProjection;
import com.ems.projection.EmployeeSummaryDTO;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Exercise 4: Service layer wrapping CRUD operations
 * Exercise 6: Pagination + Sorting helpers
 * Exercise 8: Returns projections
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    // ---- Exercise 4: CRUD ----

    @Transactional
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPosition(updated.getPosition());
        existing.setSalary(updated.getSalary());
        existing.setHireDate(updated.getHireDate());
        if (updated.getDepartment() != null) {
            existing.setDepartment(updated.getDepartment());
        }
        return employeeRepository.save(existing);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // ---- Exercise 5: Custom queries ----

    public List<Employee> searchByKeyword(String keyword) {
        return employeeRepository.searchByKeyword(keyword);
    }

    public List<Employee> findByDepartment(Long deptId) {
        return employeeRepository.findByDepartmentId(deptId);
    }

    public List<Employee> findBySalaryRange(BigDecimal min, BigDecimal max) {
        return employeeRepository.findBySalaryBetween(min, max);
    }

    // ---- Exercise 6: Pagination ----

    /**
     * Returns a paginated + sorted employee list.
     * @param page      zero-based page index
     * @param size      page size
     * @param sortField field to sort by
     * @param ascending true = ASC, false = DESC
     */
    public Page<Employee> findAllPaginated(int page, int size, String sortField, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> searchPaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Employee> findByDepartmentPaginated(Long deptId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeRepository.findByDepartmentId(deptId, pageable);
    }

    // ---- Exercise 8: Projections ----

    public List<EmployeeNameEmailProjection> getEmployeeNameEmailsByDepartment(Long deptId) {
        return employeeRepository.findProjectedByDepartmentId(deptId);
    }

    public List<EmployeeSummaryDTO> getActiveEmployeeSummaries() {
        return employeeRepository.findActiveEmployeeSummaries();
    }

    // ---- Helper ----

    @Transactional
    public Employee assignToDepartment(Long employeeId, Long departmentId) {
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));
        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found: " + departmentId));
        emp.setDepartment(dept);
        return employeeRepository.save(emp);
    }
}
