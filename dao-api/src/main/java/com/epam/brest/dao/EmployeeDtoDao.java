package com.epam.brest.dao;

import com.epam.brest.model.dto.EmployeeDto;

import java.sql.Date;
import java.util.List;

public interface EmployeeDtoDao {

    /**
     * Returns all Employees
     */
    List<EmployeeDto> findAll();

    /**
     * Returns all Employees hired in the specified date period
     */
    List<EmployeeDto> findByDate(Date firstDate, Date secondDate);

}
