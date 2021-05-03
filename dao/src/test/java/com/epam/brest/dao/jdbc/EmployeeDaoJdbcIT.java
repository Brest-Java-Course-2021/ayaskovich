package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Employee;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@DataJdbcTest
@Import({EmployeeDaoJdbc.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeDaoJdbcIT {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void findAllEmployeesTest() {
        List<Employee> employeeList = employeeDao.findAll();
        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0);
    }

    @Test
    public void findByDateTest() {
        List<Employee> employeeList = employeeDao.findAll();
        Assertions.assertNotNull(employeeList);

        Employee updaateEmployee = employeeList.get(0);
        Date updateEmployeeHared = Date.valueOf("2005-01-12");
        updaateEmployee.setHired(updateEmployeeHared);
        Integer result = employeeDao.update(updaateEmployee);

        List<Employee> newEmployeeList = employeeDao.findAll();
        Assertions.assertEquals(updateEmployeeHared, newEmployeeList.get(0).getHired());

        List<Employee> employeesByDate = employeeDao.findAByDate(Date.valueOf("2000-01-01"), Date.valueOf("2010-01-01"));
        Assertions.assertNotNull(employeesByDate);
        Assertions.assertTrue(employeesByDate.size() > 0);
    }

    @Test
    public void findByIdTest() {
        List<Employee> employeeList = employeeDao.findAll();
        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0);

        Integer employeeId = employeeList.get(0).getEmployeeId();
        Employee expectedEmployee = employeeDao.findById(employeeId).get();
        Assertions.assertEquals(employeeId, expectedEmployee.getEmployeeId());
        Assertions.assertEquals(employeeList.get(0).getEmail(), expectedEmployee.getEmail());
        Assertions.assertEquals(employeeList.get(0), expectedEmployee);
    }

    @Test
    public void createEmployeeTest() {
        List<Employee> employeeList = employeeDao.findAll();
        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0);

        Employee employee = new Employee(
                "FirstName",
                "LastName",
                "first@mail.com",
                100.0,
                1,
                new Date(Calendar.getInstance().getTime().getTime()));
        Integer result = employeeDao.create(employee);

        List<Employee> newEmployeeList = employeeDao.findAll();
        Assertions.assertEquals(employeeList.size()+1, newEmployeeList.size());
    }

    @Test
    public void updateEmployeeTest() {
        List<Employee> employeeList = employeeDao.findAll();
        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0);

        Employee employee = employeeList.get(0);
        employee.setEmail("TEST_EMAIL");
        employee.setHired(new Date(2021, 4, 24));
        employeeDao.update(employee);

        Optional<Employee> newEmployee = employeeDao.findById(employee.getEmployeeId());
        Assertions.assertEquals("TEST_EMAIL", newEmployee.get().getEmail());
        Assertions.assertEquals(new Date(2021, 4, 24), newEmployee.get().getHired());
    }

    @Test
    public void deleteEmployeeTest() {
        List<Employee> employeeList = employeeDao.findAll();
        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0);

        Integer employeeId = employeeList.get(0).getEmployeeId();
        employeeDao.delete(employeeId);

        List<Employee> newEmployeeList = employeeDao.findAll();
        Assertions.assertEquals(employeeList.size(), newEmployeeList.size()+1);
    }

}