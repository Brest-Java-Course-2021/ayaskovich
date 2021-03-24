package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.model.Department;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class DepartmentDaoJdbcTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void findAll() {
        List<Department> departmentList = departmentDao.findAll();
        Assert.assertNotNull(departmentList);
        Assert.assertTrue(departmentList.size() > 0);
    }

    @Test
    public void findByIdTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assert.assertNotNull(departmentList);
        Assert.assertTrue(departmentList.size() > 0);

        Integer departmentId = departmentList.get(0).getDepartmentId();
        Department expectedDepartment = departmentDao.findById(departmentId).get();
        Assert.assertEquals(departmentId, expectedDepartment.getDepartmentId());
        Assert.assertEquals(departmentList.get(0).getDepartmentName(), expectedDepartment.getDepartmentName());
        Assert.assertEquals(departmentList.get(0), expectedDepartment);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void findByIdExceptionalTest() {
        departmentDao.findById(999);
    }

    @Test
    public void createDepartmentTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assert.assertNotNull(departmentList);
        Assert.assertTrue(departmentList.size() > 0);

        Department department = new Department("HR");
        Integer result = departmentDao.create(department);

        List<Department> newDepartmentList = departmentDao.findAll();
        Assert.assertEquals(departmentList.size()+1, newDepartmentList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDepartmentWithSameNameTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assert.assertNotNull(departmentList);
        Assert.assertTrue(departmentList.size() > 0);

        departmentDao.create(new Department("HR"));
        departmentDao.create(new Department("HR"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDepartmentWithSameNameDifferentCaseTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assert.assertNotNull(departmentList);
        Assert.assertTrue(departmentList.size() > 0);

        departmentDao.create(new Department("HR"));
        departmentDao.create(new Department("Hr"));
    }

    @Test
    public void updateDepartmentTest() {
        List<Department> departmentList = departmentDao.findAll();
        Assert.assertNotNull(departmentList);
        Assert.assertTrue(departmentList.size() > 0);

        Department department = departmentList.get(0);
        department.setDepartmentName("TEST_DEPARTMENT");
        departmentDao.update(department);

        Optional<Department> newDepartment = departmentDao.findById(department.getDepartmentId());
        Assert.assertEquals("TEST_DEPARTMENT", newDepartment.get().getDepartmentName());
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