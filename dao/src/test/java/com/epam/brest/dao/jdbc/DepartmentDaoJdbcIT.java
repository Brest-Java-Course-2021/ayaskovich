package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.model.Department;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@DataJdbcTest
@Import({DepartmentDaoJdbc.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentDaoJdbcIT {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void findAll() {
        List<Department> departmentList = departmentDao.findAll();
        Assertions.assertNotNull(departmentList);
        Assertions.assertTrue(departmentList.size() > 0);
    }

    @Test
    public void findByIdTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assertions.assertNotNull(departmentList);
        Assertions.assertTrue(departmentList.size() > 0);

        Integer departmentId = departmentList.get(0).getDepartmentId();
        Department expectedDepartment = departmentDao.findById(departmentId).get();
        Assertions.assertEquals(departmentId, expectedDepartment.getDepartmentId());
        Assertions.assertEquals(departmentList.get(0).getDepartmentName(), expectedDepartment.getDepartmentName());
        Assertions.assertEquals(departmentList.get(0), expectedDepartment);
    }

    @Test
    public void createDepartmentTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assertions.assertNotNull(departmentList);
        Assertions.assertTrue(departmentList.size() > 0);

        Department department = new Department("HR");
        Integer result = departmentDao.create(department);

        List<Department> newDepartmentList = departmentDao.findAll();
        Assertions.assertEquals(departmentList.size()+1, newDepartmentList.size());
    }

    @Test
    public void createDepartmentWithSameNameTest() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            List<Department> departmentList = departmentDao.findAll();
            Assertions.assertNotNull(departmentList);
            Assertions.assertTrue(departmentList.size() > 0);

            departmentDao.create(new Department("HR"));
            departmentDao.create(new Department("HR"));
        });

    }

    @Test
    public void createDepartmentWithSameNameDifferentCaseTest() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            List<Department> departmentList = departmentDao.findAll();
            Assertions.assertNotNull(departmentList);
            Assertions.assertTrue(departmentList.size() > 0);

            departmentDao.create(new Department("HR"));
            departmentDao.create(new Department("Hr"));
        });
    }

    @Test
    public void updateDepartmentTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assertions.assertNotNull(departmentList);
        Assertions.assertTrue(departmentList.size() > 0);

        Department department = departmentList.get(0);
        department.setDepartmentName("TEST_DEPARTMENT");
        departmentDao.update(department);

        Optional<Department> newDepartment = departmentDao.findById(department.getDepartmentId());
        Assertions.assertEquals("TEST_DEPARTMENT", newDepartment.get().getDepartmentName());
    }

//    @Test
//    public void updateDepartmentNotUniqueNameTest() {
//        List<Department> departmentList = departmentDao.findAll();
//        Assert.assertNotNull(departmentList);
//        Assert.assertTrue(departmentList.size() > 0);
//
//        Department department = departmentList.get(0);
//        department.setDepartmentName(departmentList.get(1).getDepartmentName());
//        departmentDao.update(department);
//    }
}