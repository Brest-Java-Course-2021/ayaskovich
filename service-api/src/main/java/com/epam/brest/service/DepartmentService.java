package com.epam.brest.service;

import com.epam.brest.model.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    /**
     * Returns all Departments
     */
    List<Department> findAll();
    /**
     * Return Department by ID
     */
    Optional<Department> findById(Integer departmentId);

    /**
     * Create new Department
     */
    Integer create(Department department);

    /**
     * Edit Department
     */
    Integer update(Department department);

    /**
     * Delete Department
     */
    Integer delete(Integer departmentId);

    /**
     * Count Departments
     */
    Integer count();

}
