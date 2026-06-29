package com.ems;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.service.BatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exercise 10: Batch processing tests
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BatchServiceTest {

    @Autowired private BatchService batchService;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private DepartmentRepository departmentRepository;

    @Test @DisplayName("Ex10: Batch insert 50 employees")
    void batchInsert() {
        Department dept = departmentRepository.findByName("Engineering").orElseThrow();
        List<Employee> employees = batchService.buildSampleEmployees(dept, 50);
        long before = employeeRepository.count();
        batchService.batchInsertEmployees(employees);
        assertThat(employeeRepository.count()).isEqualTo(before + 50);
    }

    @Test @DisplayName("Ex10: Bulk deactivate by department")
    void bulkDeactivate() {
        Department dept = departmentRepository.findByName("Engineering").orElseThrow();
        int updated = batchService.bulkDeactivateByDepartment(dept.getId());
        assertThat(updated).isGreaterThanOrEqualTo(1);

        // Verify active=false
        List<Employee> stillActive = employeeRepository.findByDepartmentId(dept.getId())
            .stream().filter(Employee::getActive).toList();
        assertThat(stillActive).isEmpty();
    }

    @Test @DisplayName("Ex10: Process in chunks without error")
    void processChunks() {
        // Should complete without exception
        batchService.processBatchesInChunks(3);
    }
}
