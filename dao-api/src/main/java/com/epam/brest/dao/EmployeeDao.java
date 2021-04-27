package com.epam.brest.dao;


import com.epam.brest.model.Employee;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    /**
     * Returns all Employees
     */
    List<Employee> findAll();

    /**
     * Returns all Employees hired in the specified date period
     */
    List<Employee> findAByDate(Date firstDate, Date secondDate);

    /**
     * Return Employee by ID
     */
    Optional<Employee> findById(Integer employeeId);

    /**
     * Create new Employee
     */
    Integer create(Employee employee);

    /**
     * Edit Department
     */
    Integer update(Employee employee);

    /**
     * Delete Employee
     */
    Integer delete(Integer employeeId);

}
