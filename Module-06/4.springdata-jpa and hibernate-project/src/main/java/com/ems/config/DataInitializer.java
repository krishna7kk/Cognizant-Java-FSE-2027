package com.ems.config;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Seeds the H2 in-memory database on startup.
 * Covers Exercises 1-10 by providing data for queries, pagination, auditing, projections, etc.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        log.info("=== Seeding database ===");

        // Departments
        Department engineering = departmentRepository.save(
            Department.builder().name("Engineering").description("Software development team").build());
        Department hr = departmentRepository.save(
            Department.builder().name("Human Resources").description("People and culture").build());
        Department finance = departmentRepository.save(
            Department.builder().name("Finance").description("Financial operations").build());
        Department marketing = departmentRepository.save(
            Department.builder().name("Marketing").description("Marketing and brand").build());

        // Employees - Engineering
        employeeRepository.save(Employee.builder()
            .name("Alice Johnson").email("alice@example.com").position("Senior Engineer")
            .salary(new BigDecimal("95000")).hireDate(LocalDate.of(2020, 3, 15))
            .department(engineering).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("Bob Smith").email("bob@example.com").position("Junior Engineer")
            .salary(new BigDecimal("60000")).hireDate(LocalDate.of(2022, 7, 1))
            .department(engineering).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("Carol White").email("carol@example.com").position("Tech Lead")
            .salary(new BigDecimal("115000")).hireDate(LocalDate.of(2018, 5, 20))
            .department(engineering).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("David Lee").email("david@example.com").position("DevOps Engineer")
            .salary(new BigDecimal("88000")).hireDate(LocalDate.of(2021, 11, 10))
            .department(engineering).active(false).build());

        // Employees - HR
        employeeRepository.save(Employee.builder()
            .name("Eve Davis").email("eve@example.com").position("HR Manager")
            .salary(new BigDecimal("72000")).hireDate(LocalDate.of(2019, 9, 5))
            .department(hr).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("Frank Miller").email("frank@example.com").position("Recruiter")
            .salary(new BigDecimal("55000")).hireDate(LocalDate.of(2023, 1, 15))
            .department(hr).active(true).build());

        // Employees - Finance
        employeeRepository.save(Employee.builder()
            .name("Grace Wilson").email("grace@example.com").position("CFO")
            .salary(new BigDecimal("145000")).hireDate(LocalDate.of(2015, 6, 1))
            .department(finance).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("Henry Brown").email("henry@example.com").position("Accountant")
            .salary(new BigDecimal("65000")).hireDate(LocalDate.of(2021, 4, 20))
            .department(finance).active(true).build());

        // Employees - Marketing
        employeeRepository.save(Employee.builder()
            .name("Iris Clark").email("iris@example.com").position("Marketing Manager")
            .salary(new BigDecimal("78000")).hireDate(LocalDate.of(2020, 8, 12))
            .department(marketing).active(true).build());

        employeeRepository.save(Employee.builder()
            .name("Jack Turner").email("jack@example.com").position("Content Writer")
            .salary(new BigDecimal("52000")).hireDate(LocalDate.of(2022, 3, 7))
            .department(marketing).active(true).build());

        log.info("=== Seeding complete: {} departments, {} employees ===",
            departmentRepository.count(), employeeRepository.count());
    }
}
