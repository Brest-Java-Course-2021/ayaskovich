package com.epam.brest.service.impl;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Employee;
import com.epam.brest.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * Returns all Employees
     */
    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    /**
     * Returns all employees hired in the specified date period
     */
    @Override
    public List<Employee> findByDate(Date firstDate, Date secondDate) {
        return employeeDao.findAByDate(firstDate, secondDate);
    }

    /**
     * Return Employee by ID
     */
    @Override
    public Optional<Employee> findById(Integer employeeId) {
        return employeeDao.findById(employeeId);
    }

    /**
     * Create new Employee
     */
    @Override
    public Integer create(Employee employee) {
        return employeeDao.create(employee);
    }

    /**
     * Edit Department
     */
    @Override
    public Integer update(Employee employee) {
        return employeeDao.update(employee);
    }

    /**
     * Delete Employee
     */
    @Override
    public Integer delete(Integer employeeId) {
        return employeeDao.delete(employeeId);
    }
}
