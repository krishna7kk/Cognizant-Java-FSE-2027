package com.cognizant.ormlearn.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Persistence class for the DEPARTMENT table.
 *
 * NOTE on fetch type (Hands On 2): employeeList is mapped EAGER here on
 * purpose, matching the handout's starting state ("Eager fetch configuration
 * is defined for employeeList in Department.java"). The hands-on exercise
 * walks through removing this EAGER setting to observe the resulting N+1
 * query problem, then fixing it properly using 'join fetch' in HQL instead
 * of relying on entity-level eager fetching.
 */
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dp_id")
    private Integer id;

    @Column(name = "dp_name")
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private List<Employee> employeeList;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }
}
