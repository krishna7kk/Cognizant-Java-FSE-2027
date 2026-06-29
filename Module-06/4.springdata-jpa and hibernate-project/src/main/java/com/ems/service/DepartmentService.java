package com.ems.service;

import com.ems.entity.Department;
import com.ems.projection.DepartmentProjection;
import com.ems.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 4: CRUD service for Department
 * Exercise 6: Pagination
 * Exercise 8: Projections
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    // ---- Exercise 4: CRUD ----

    @Transactional
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new IllegalArgumentException("Department already exists: " + department.getName());
        }
        return departmentRepository.save(department);
    }

    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    public List<Department> findAll() {
        return departmentRepository.findAll(Sort.by("name").ascending());
    }

    public List<Department> findAllWithEmployees() {
        return departmentRepository.findAllWithEmployees();
    }

    @Transactional
    public Department updateDepartment(Long id, Department updated) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found: " + id));
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return departmentRepository.save(existing);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    // ---- Exercise 6: Pagination ----

    public Page<Department> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return departmentRepository.findAll(pageable);
    }

    public Page<Department> searchPaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return departmentRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    // ---- Exercise 8: Projections ----

    public List<DepartmentProjection> getDepartmentProjections() {
        return departmentRepository.findProjectedBy();
    }

    // ---- Exercise 5: Named query ----

    public List<Department> searchByNameNamed(String name) {
        return departmentRepository.findByNameContainingNamed(name);
    }
}
