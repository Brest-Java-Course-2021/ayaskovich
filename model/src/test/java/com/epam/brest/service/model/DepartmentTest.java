package com.epam.brest.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Test
    public void setDepartmentNameConstructor() {
        Department department = new Department("IT");
        Assert.assertEquals("IT", department.getDepartmentName());
    }

    @Test
    public void setDepartmentNameSetter() {
        Department department = new Department();
        department.setDepartmentName("IT");
        Assert.assertEquals("IT", department.getDepartmentName());
    }
}