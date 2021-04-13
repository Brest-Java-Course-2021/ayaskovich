package com.epam.brest.rest_app.exception;

public class DepartmentNotFoundException extends RuntimeException{

    public DepartmentNotFoundException(Integer id) {
        super("Department not found for ID: " + id);
    }
}
