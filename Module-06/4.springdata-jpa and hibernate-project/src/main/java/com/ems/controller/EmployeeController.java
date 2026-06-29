package com.ems.controller;

import com.ems.entity.Employee;
import com.ems.projection.EmployeeNameEmailProjection;
import com.ems.projection.EmployeeSummaryDTO;
import com.ems.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Exercise 4 : RESTful CRUD endpoints for Employee
 * Exercise 6 : Pagination + sorting endpoints
 * Exercise 8 : Projection endpoints
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // ---- Exercise 4: CRUD ----

    /** POST /api/employees */
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employee));
    }

    /** GET /api/employees/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/employees */
    @GetMapping
    public List<Employee> getAll() {
        return employeeService.findAll();
    }

    /** PUT /api/employees/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id,
                                           @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    /** DELETE /api/employees/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Exercise 5: Custom search ----

    /** GET /api/employees/search?keyword=john */
    @GetMapping("/search")
    public List<Employee> search(@RequestParam String keyword) {
        return employeeService.searchByKeyword(keyword);
    }

    /** GET /api/employees/department/{deptId} */
    @GetMapping("/department/{deptId}")
    public List<Employee> byDepartment(@PathVariable Long deptId) {
        return employeeService.findByDepartment(deptId);
    }

    /** GET /api/employees/salary?min=50000&max=100000 */
    @GetMapping("/salary")
    public List<Employee> bySalaryRange(@RequestParam BigDecimal min,
                                        @RequestParam BigDecimal max) {
        return employeeService.findBySalaryRange(min, max);
    }

    // ---- Exercise 6: Pagination + Sorting ----

    /**
     * GET /api/employees/paginated
     *     ?page=0&size=10&sortField=name&ascending=true
     */
    @GetMapping("/paginated")
    public Page<Employee> paginated(
            @RequestParam(defaultValue = "0")    int page,
            @RequestParam(defaultValue = "10")   int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return employeeService.findAllPaginated(page, size, sortField, ascending);
    }

    /**
     * GET /api/employees/paginated/search?name=alice&page=0&size=5
     */
    @GetMapping("/paginated/search")
    public Page<Employee> paginatedSearch(
            @RequestParam String name,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return employeeService.searchPaginated(name, page, size);
    }

    /**
     * GET /api/employees/paginated/department/{deptId}?page=0&size=5
     */
    @GetMapping("/paginated/department/{deptId}")
    public Page<Employee> paginatedByDepartment(
            @PathVariable Long deptId,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return employeeService.findByDepartmentPaginated(deptId, page, size);
    }

    // ---- Exercise 8: Projections ----

    /** GET /api/employees/projections/name-email/department/{deptId} */
    @GetMapping("/projections/name-email/department/{deptId}")
    public List<EmployeeNameEmailProjection> nameEmailByDept(@PathVariable Long deptId) {
        return employeeService.getEmployeeNameEmailsByDepartment(deptId);
    }

    /** GET /api/employees/projections/summaries */
    @GetMapping("/projections/summaries")
    public List<EmployeeSummaryDTO> activeSummaries() {
        return employeeService.getActiveEmployeeSummaries();
    }

    // ---- Assign to department ----

    /** PUT /api/employees/{empId}/assign/{deptId} */
    @PutMapping("/{empId}/assign/{deptId}")
    public ResponseEntity<Employee> assignToDepartment(@PathVariable Long empId,
                                                       @PathVariable Long deptId) {
        return ResponseEntity.ok(employeeService.assignToDepartment(empId, deptId));
    }
}
