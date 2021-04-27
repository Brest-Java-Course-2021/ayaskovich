package com.epam.brest.service.impl;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    /**
     * Returns all Departments
     */
    @Override
    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    /**
     * Return Department by ID
     */
    @Override
    public Optional<Department> findById(Integer departmentId) {
        return departmentDao.findById(departmentId);
    }

    /**
     * Create new Department
     */
    @Override
    public Integer create(Department department) {
        return departmentDao.create(department);
    }

    /**
     * Edit Department
     */
    @Override
    public Integer update(Department department) {
        return departmentDao.update(department);
    }

    /**
     * Delete Department
     */
    @Override
    public Integer delete(Integer departmentId) {
        return departmentDao.delete(departmentId);
    }

    /**
     * Count Departments
     */
    @Override
    public Integer count() {
        return departmentDao.count();
    }
}
