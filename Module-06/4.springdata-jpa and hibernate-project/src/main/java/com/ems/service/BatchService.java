package com.ems.service;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 10: Batch Processing with Hibernate.
 *
 * Hibernate batch processing avoids sending one SQL per row.
 * Requires in application.properties:
 *   spring.jpa.properties.hibernate.jdbc.batch_size=20
 *   spring.jpa.properties.hibernate.order_inserts=true
 *   spring.jpa.properties.hibernate.order_updates=true
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BatchService {

    @PersistenceContext
    private EntityManager entityManager;

    private final EmployeeRepository employeeRepository;

    private static final int BATCH_SIZE = 20;

    /**
     * Bulk insert employees using EntityManager flush + clear pattern.
     * This is the correct way to do Hibernate batch inserts.
     */
    @Transactional
    public void batchInsertEmployees(List<Employee> employees) {
        log.info("Starting batch insert of {} employees", employees.size());
        int count = 0;
        for (Employee emp : employees) {
            entityManager.persist(emp);
            count++;
            if (count % BATCH_SIZE == 0) {
                entityManager.flush();   // sends batch to DB
                entityManager.clear();   // evicts from 1st-level cache
                log.debug("Flushed batch at count {}", count);
            }
        }
        // Flush any remaining
        entityManager.flush();
        entityManager.clear();
        log.info("Batch insert complete. Total: {}", count);
    }

    /**
     * Bulk update using JPQL UPDATE (single SQL statement, no object tracking overhead).
     */
    @Transactional
    public int bulkDeactivateByDepartment(Long departmentId) {
        int updated = employeeRepository.deactivateByDepartment(departmentId);
        log.info("Deactivated {} employees in department {}", updated, departmentId);
        return updated;
    }

    /**
     * Demonstrates reading in batches using pagination to avoid loading
     * everything into memory at once.
     */
    @Transactional(readOnly = true)
    public void processBatchesInChunks(int chunkSize) {
        long total = employeeRepository.count();
        int pages = (int) Math.ceil((double) total / chunkSize);
        log.info("Processing {} employees in {} chunks of {}", total, pages, chunkSize);

        for (int page = 0; page < pages; page++) {
            List<Employee> chunk = employeeRepository
                    .findAll(org.springframework.data.domain.PageRequest.of(page, chunkSize))
                    .getContent();
            log.info("Processing chunk {} ({} records)", page + 1, chunk.size());
            // process chunk here ...
        }
    }

    /**
     * Builds a list of sample employees for batch testing.
     */
    public List<Employee> buildSampleEmployees(com.ems.entity.Department dept, int count) {
        List<Employee> list = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            list.add(Employee.builder()
                    .name("BatchEmployee_" + i)
                    .email("batch" + i + "@example.com")
                    .position("Engineer")
                    .department(dept)
                    .active(true)
                    .build());
        }
        return list;
    }
}
