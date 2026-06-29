package com.cognizant.ormlearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Hands On 2: get all permanent employees, including department and
     * skill list, via the optimized HQL ('left join fetch') query.
     */
    @Transactional
    public List<Employee> getAllPermanentEmployees() {
        return employeeRepository.getAllPermanentEmployees();
    }

    /**
     * Hands On 4: get the average salary across all employees in a given
     * department, using an HQL aggregate function with a named parameter.
     */
    @Transactional
    public double getAverageSalaryByDepartment(int departmentId) {
        return employeeRepository.getAverageSalaryByDepartment(departmentId);
    }

    /**
     * Hands On 5: get all employees using a native SQL query instead of
     * HQL/JPQL.
     */
    @Transactional
    public List<Employee> getAllEmployeesNative() {
        return employeeRepository.getAllEmployeesNative();
    }
}
