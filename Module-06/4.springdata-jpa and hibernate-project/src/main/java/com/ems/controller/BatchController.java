package com.ems.controller;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Exercise 10: REST endpoints to demonstrate Hibernate batch processing.
 */
@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;
    private final DepartmentRepository departmentRepository;

    /**
     * POST /api/batch/insert/{deptId}?count=50
     * Inserts {count} sample employees into department {deptId} using Hibernate batch.
     */
    @PostMapping("/insert/{deptId}")
    public ResponseEntity<Map<String, Object>> batchInsert(
            @PathVariable Long deptId,
            @RequestParam(defaultValue = "50") int count) {

        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found: " + deptId));

        List<Employee> employees = batchService.buildSampleEmployees(dept, count);
        batchService.batchInsertEmployees(employees);

        return ResponseEntity.ok(Map.of(
                "message", "Batch insert complete",
                "inserted", count,
                "departmentId", deptId
        ));
    }

    /**
     * PUT /api/batch/deactivate/{deptId}
     * Bulk-deactivates all employees in a department (single UPDATE statement).
     */
    @PutMapping("/deactivate/{deptId}")
    public ResponseEntity<Map<String, Object>> bulkDeactivate(@PathVariable Long deptId) {
        int updated = batchService.bulkDeactivateByDepartment(deptId);
        return ResponseEntity.ok(Map.of(
                "message", "Bulk deactivate complete",
                "updatedCount", updated,
                "departmentId", deptId
        ));
    }

    /**
     * GET /api/batch/process-chunks?chunkSize=20
     * Demonstrates chunk-based processing without loading all records into memory.
     */
    @GetMapping("/process-chunks")
    public ResponseEntity<Map<String, String>> processChunks(
            @RequestParam(defaultValue = "20") int chunkSize) {
        batchService.processBatchesInChunks(chunkSize);
        return ResponseEntity.ok(Map.of("message", "Chunk processing triggered — check logs"));
    }
}
