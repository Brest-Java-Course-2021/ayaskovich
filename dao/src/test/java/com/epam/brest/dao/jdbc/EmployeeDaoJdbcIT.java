package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
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

        Employee employee = new Employee("FirstName", "LastName", "first@mail.com", 100.0, 1);
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
        employeeDao.update(employee);

        Optional<Employee> newEmployee = employeeDao.findById(employee.getEmployeeId());
        Assertions.assertEquals("TEST_EMAIL", newEmployee.get().getEmail());
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