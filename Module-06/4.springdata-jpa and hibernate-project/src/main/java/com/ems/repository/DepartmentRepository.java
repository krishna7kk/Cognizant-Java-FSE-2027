package com.ems.repository;

import com.ems.entity.Department;
import com.ems.projection.DepartmentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 3: DepartmentRepository extending JpaRepository
 * Exercise 5: Derived queries + @Query
 * Exercise 6: Pagination
 * Exercise 8: Projections
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Exercise 3 & 5: Derived query methods
    Optional<Department> findByName(String name);

    List<Department> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);

    // Exercise 5: @Query JPQL
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees")
    List<Department> findAllWithEmployees();

    @Query("SELECT d FROM Department d WHERE SIZE(d.employees) > :count")
    List<Department> findDepartmentsWithMoreThanNEmployees(@Param("count") int count);

    @Query("SELECT d.name, COUNT(e) FROM Department d LEFT JOIN d.employees e GROUP BY d.name")
    List<Object[]> findDepartmentEmployeeCount();

    // Exercise 6: Pagination
    Page<Department> findAll(Pageable pageable);

    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Exercise 8: Interface-based projection
    List<DepartmentProjection> findProjectedBy();

    // Exercise 5: Named Query reference (defined in Department entity)
    @Query(name = "Department.findByNameContaining")
    List<Department> findByNameContainingNamed(@Param("name") String name);
}
