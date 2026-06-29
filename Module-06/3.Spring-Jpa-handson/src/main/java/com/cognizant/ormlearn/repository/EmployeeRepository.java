package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /*
     * Hands On 2: Get all permanent employees using HQL.
     *
     * Progression followed in the handout (kept here as comments for
     * reference - the final, optimized version is the active method below):
     *
     * Step 1 - naive HQL (relies on entity-level EAGER fetch, works but is
     * driven by Department/Employee EAGER annotations rather than the query
     * itself):
     *   @Query(value = "SELECT e FROM Employee e WHERE e.permanent = 1")
     *   List<Employee> getAllPermanentEmployees();
     *
     * Step 2 - after removing EAGER from Department.employeeList and
     * Employee.skillList, the same query above no longer returns skill
     * details (lazy loading outside a session means the data isn't there).
     *
     * Step 3 - adding 'join' alone (without 'fetch') still does not populate
     * the related beans, it only filters/links the SQL join:
     *   @Query(value = "SELECT e FROM Employee e left join e.department d
     *                    left join e.skillList WHERE e.permanent = 1")
     *
     * Step 4 (FINAL, used here) - 'left join fetch' populates the related
     * beans in a single SQL query. This is the optimized solution.
     */
    @Query(value = "SELECT e FROM Employee e left join fetch e.department d left join fetch e.skillList WHERE e.permanent = 1")
    List<Employee> getAllPermanentEmployees();

    /*
     * Hands On 4: Get average salary using HQL aggregate function.
     *
     * Step 1 - unfiltered average across all employees:
     *   @Query(value = "SELECT AVG(e.salary) FROM Employee e")
     *   double getAverageSalary();
     *
     * Step 2 (FINAL, used here) - filtered by department id using a named
     * parameter. Note how 'e.department.id' navigates the association path
     * directly in HQL, and ':id' is bound via @Param.
     */
    @Query(value = "SELECT AVG(e.salary) FROM Employee e where e.department.id = :id")
    double getAverageSalaryByDepartment(@Param("id") int id);

    /*
     * Hands On 5: Get all employees using a Native Query.
     * nativeQuery = true tells Spring Data JPA to execute this as raw SQL
     * against the underlying database rather than parsing it as HQL/JPQL.
     */
    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> getAllEmployeesNative();
}
