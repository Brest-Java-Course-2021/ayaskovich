package com.epam.brest.service.impl;

import com.epam.brest.dao.EmployeeDtoDao;
import com.epam.brest.model.dto.EmployeeDto;
import com.epam.brest.service.EmployeeDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class EmployeeDtoServiceImpl implements EmployeeDtoService {

    private final EmployeeDtoDao employeeDtoDao;

    public EmployeeDtoServiceImpl(EmployeeDtoDao employeeDtoDao) {
        this.employeeDtoDao = employeeDtoDao;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeDtoDao.findAll();
    }

    @Override
    public List<EmployeeDto> findByDate(Date firstDate, Date secondDate) {
        return employeeDtoDao.findByDate(firstDate, secondDate);
    }
}
