package com.epam.brest.rest_app.exception;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Integer id) {
        super("Employee not found for ID: " + id);
    }

}
