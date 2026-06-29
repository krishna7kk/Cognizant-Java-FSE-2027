package com.cognizant.ormlearn.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Persistence class for the EMPLOYEE table.
 *
 * NOTE on fetch type (Hands On 2): skillList is mapped EAGER here on purpose,
 * matching the handout's starting state ("Eager fetch configuration is
 * defined for ... skillList in Employee.java"). The hands-on exercise walks
 * through removing this EAGER setting, observing the resulting incomplete
 * data, and then fixing it properly using 'join fetch' in HQL at the query
 * level instead of relying on entity-level eager fetching.
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "em_id")
    private Integer id;

    @Column(name = "em_name")
    private String name;

    @Column(name = "em_date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "em_permanent")
    private boolean permanent;

    @Column(name = "em_salary")
    private double salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "em_dp_id")
    private Department department;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "employee_skill",
        joinColumns = @JoinColumn(name = "es_em_id"),
        inverseJoinColumns = @JoinColumn(name = "es_sk_id")
    )
    private List<Skill> skillList;

    public Employee() {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth
            + ", permanent=" + permanent + ", salary=" + salary + "]";
    }
}
