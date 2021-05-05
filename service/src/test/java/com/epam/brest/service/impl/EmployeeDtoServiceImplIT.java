package com.epam.brest.service.impl;

import com.epam.brest.dao.jdbc.EmployeeDaoJdbc;
import com.epam.brest.dao.jdbc.EmployeeDtoDaoJdbc;
import com.epam.brest.model.Employee;
import com.epam.brest.model.dto.EmployeeDto;
import com.epam.brest.service.EmployeeDtoService;
import com.epam.brest.service.EmployeeService;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import({EmployeeDtoServiceImpl.class, EmployeeDtoDaoJdbc.class, EmployeeServiceImpl.class, EmployeeDaoJdbc.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@ComponentScan(basePackages = {"com.epam.brest.dao", "com.epam.brest.testdb"})
@Transactional
class EmployeeDtoServiceImplIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDtoServiceImplIT.class);

    @Autowired
    EmployeeDtoService employeeDtoService;

    @Autowired
    EmployeeService employeeService;

    @Test
    void findAllTest() {
        LOGGER.debug("findAll()");
        List<EmployeeDto> employeeDtoList = employeeDtoService.findAll();
        assertNotNull(employeeDtoList);
        assertTrue(employeeDtoList.size() > 0);
        assertTrue(employeeDtoList.get(0).getDepartmentName().length() > 0);
    }

    @Test
    void findByDateTest() {
        LOGGER.debug("findByDate({},{}, firstDate, secondDate)");
        List<Employee> employeeList = employeeService.findAll();
        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0);

        Employee updaateEmployee = employeeList.get(0);
        Date updateEmployeeHared = Date.valueOf("2005-01-12");
        updaateEmployee.setHired(updateEmployeeHared);
        Integer result = employeeService.update(updaateEmployee);

        List<Employee> newEmployeeList = employeeService.findAll();
        Assertions.assertEquals(updateEmployeeHared, newEmployeeList.get(0).getHired());

        List<EmployeeDto> employeesByDate = employeeDtoService.findByDate(Date.valueOf("2005-01-01"), Date.valueOf("2005-02-01"));
        Assertions.assertNotNull(employeesByDate);
        Assertions.assertTrue(employeesByDate.size() > 0);
        Assertions.assertTrue(employeesByDate.size() == 1);

    }

}