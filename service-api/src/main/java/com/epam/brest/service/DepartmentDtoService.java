package com.epam.brest.service;

import com.epam.brest.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoService {

    /**
     * Returns all Departments with average salaries
     */
    List<DepartmentDto> findAllWithAvgSalary();
}
