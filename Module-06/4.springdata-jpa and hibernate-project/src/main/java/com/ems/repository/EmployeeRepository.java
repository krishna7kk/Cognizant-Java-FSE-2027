package com.ems.repository;

import com.ems.entity.Employee;
import com.ems.projection.EmployeeNameEmailProjection;
import com.ems.projection.EmployeeSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Exercise 3: Repository extending JpaRepository (CRUD for free)
 * Exercise 5: Derived query methods + @Query (JPQL & native)
 * Exercise 6: Pagination (Page/Pageable) + Sorting
 * Exercise 8: Projection return types
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // -------------------------------------------------------
    // Exercise 3 & 5: Derived Query Methods (keywords in name)
    // -------------------------------------------------------

    /** Find by exact email */
    Optional<Employee> findByEmail(String email);

    /** Find all employees whose name contains the given string (case-insensitive) */
    List<Employee> findByNameContainingIgnoreCase(String name);

    /** Find all employees belonging to a department by department id */
    List<Employee> findByDepartmentId(Long departmentId);

    /** Find all employees belonging to a department by department name */
    List<Employee> findByDepartmentName(String departmentName);

    /** Find active / inactive employees */
    List<Employee> findByActive(Boolean active);

    /** Find employees with salary greater than a threshold */
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    /** Find employees with salary between two values */
    List<Employee> findBySalaryBetween(BigDecimal min, BigDecimal max);

    /** Count employees in a department */
    long countByDepartmentId(Long departmentId);

    /** Check if email already exists */
    boolean existsByEmail(String email);

    // -------------------------------------------------------
    // Exercise 5: @Query – JPQL
    // -------------------------------------------------------

    /** Fetch employees with their department in one query (avoids N+1) */
    @Query("SELECT e FROM Employee e JOIN FETCH e.department d")
    List<Employee> findAllWithDepartment();

    /** Search by name or email */
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchByKeyword(@Param("keyword") String keyword);

    /** Top earners in a department */
    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId " +
           "ORDER BY e.salary DESC")
    List<Employee> findTopEarnersByDepartment(@Param("deptId") Long deptId, Pageable pageable);

    /** Average salary per department */
    @Query("SELECT e.department.name, AVG(e.salary) FROM Employee e " +
           "GROUP BY e.department.name")
    List<Object[]> findAvgSalaryByDepartment();

    /** Exercise 5: Native SQL query */
    @Query(value = "SELECT * FROM employees WHERE active = true ORDER BY name",
           nativeQuery = true)
    List<Employee> findAllActiveNative();

    /** Exercise 5: Named Query reference */
    List<Employee> findByDepartmentName_NamedQuery(@Param("deptName") String deptName);

    // -------------------------------------------------------
    // Exercise 6: Pagination + Sorting
    // -------------------------------------------------------

    /** Paginated list of all employees */
    Page<Employee> findAll(Pageable pageable);

    /** Paginated search by name */
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /** Paginated list by department */
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    /** Paginated active employees */
    Page<Employee> findByActive(Boolean active, Pageable pageable);

    /** Sorted list (uses Sort from Spring Data) */
    List<Employee> findByDepartmentId(Long departmentId, Sort sort);

    // -------------------------------------------------------
    // Exercise 8: Projections
    // -------------------------------------------------------

    /** Interface-based projection – only name + email */
    List<EmployeeNameEmailProjection> findProjectedByDepartmentId(Long departmentId);

    /** Class-based (DTO) projection */
    @Query("SELECT new com.ems.projection.EmployeeSummaryDTO(e.id, e.name, e.email, e.position, d.name) " +
           "FROM Employee e JOIN e.department d WHERE e.active = true")
    List<EmployeeSummaryDTO> findActiveEmployeeSummaries();

    // -------------------------------------------------------
    // Bulk update (Exercise 10 – batch processing support)
    // -------------------------------------------------------

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.active = false WHERE e.department.id = :deptId")
    int deactivateByDepartment(@Param("deptId") Long deptId);
}
