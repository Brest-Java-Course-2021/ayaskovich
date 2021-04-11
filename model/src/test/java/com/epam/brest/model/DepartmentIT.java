package com.epam.brest.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DepartmentIT {

    @Test
    public void getDepartmentNameConstructorTest() {
        Department department = new Department("IT");
        Assertions.assertEquals("IT", department.getDepartmentName());
    }

    @Test
    public void getDepartmentNameSetterTest() {
        Department department = new Department();
        department.setDepartmentName("IT");
        Assertions.assertEquals("IT", department.getDepartmentName());
    }
}