package com.epam.brest.rest_app;

import com.epam.brest.model.Department;
import com.epam.brest.rest_app.exception.DepartmentNotFoundException;
import com.epam.brest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/departments")
    public Collection<Department> departments() {
        return departmentService.findAll();
    }

    @GetMapping(value = "/departments/{id}")
    public Department findById(@PathVariable Integer id) {
        return departmentService.findById(id).orElseThrow(()-> new DepartmentNotFoundException(id));
    }
}
