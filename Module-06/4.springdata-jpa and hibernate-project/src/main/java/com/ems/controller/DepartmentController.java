package com.ems.controller;

import com.ems.entity.Department;
import com.ems.projection.DepartmentProjection;
import com.ems.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exercise 4: RESTful CRUD endpoints for Department
 * Exercise 6: Pagination endpoints
 * Exercise 8: Projection endpoints
 */
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // ---- Exercise 4: CRUD ----

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(department));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable Long id) {
        return departmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Department> getAll() {
        return departmentService.findAll();
    }

    @GetMapping("/with-employees")
    public List<Department> getAllWithEmployees() {
        return departmentService.findAllWithEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id,
                                             @RequestBody Department department) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Exercise 6: Pagination ----

    /** GET /api/departments/paginated?page=0&size=5 */
    @GetMapping("/paginated")
    public Page<Department> paginated(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return departmentService.findAllPaginated(page, size);
    }

    /** GET /api/departments/paginated/search?name=eng&page=0&size=5 */
    @GetMapping("/paginated/search")
    public Page<Department> paginatedSearch(
            @RequestParam String name,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return departmentService.searchPaginated(name, page, size);
    }

    // ---- Exercise 8: Projections ----

    /** GET /api/departments/projections */
    @GetMapping("/projections")
    public List<DepartmentProjection> projections() {
        return departmentService.getDepartmentProjections();
    }

    // ---- Exercise 5: Named query ----

    /** GET /api/departments/named-search?name=eng */
    @GetMapping("/named-search")
    public List<Department> namedSearch(@RequestParam String name) {
        return departmentService.searchByNameNamed(name);
    }
}
