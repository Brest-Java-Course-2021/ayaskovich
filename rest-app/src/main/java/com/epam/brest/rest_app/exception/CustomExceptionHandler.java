package com.epam.brest.rest_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DepartmentNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleDepartmentNotFound(DepartmentNotFoundException e) {
        ErrorResponse error = new ErrorResponse("Department.not_found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmployeeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException e) {
        ErrorResponse error = new ErrorResponse("Employee.not_found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException() {
        return new ResponseEntity<>(new ErrorResponse("validation_error"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
