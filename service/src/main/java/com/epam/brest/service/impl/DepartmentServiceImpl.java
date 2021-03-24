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

    @Override
    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        return departmentDao.findById(departmentId);
    }

    @Override
    public Integer create(Department department) {
        return departmentDao.create(department);
    }

    @Override
    public Integer update(Department department) {
        return departmentDao.update(department);
    }

    @Override
    public Integer delete() {
        return departmentDao.delete();
    }
}
