package com.epam.brest.dao;

import com.epam.brest.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoDao {

    /**
     * Returns all Departments with average salaries
     */
    List<DepartmentDto> findAllWithAvgSalary();
}
