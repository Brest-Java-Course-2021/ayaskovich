package com.epam.brest.dao;

import com.epam.brest.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoDao {

    List<DepartmentDto> findAllWithAvgSalary();
}
