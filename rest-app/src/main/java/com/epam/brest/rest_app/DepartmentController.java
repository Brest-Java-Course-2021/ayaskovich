package com.epam.brest.rest_app;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Department> findById(@PathVariable Integer id) {
        Optional<Department> optional = departmentService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/departments", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createDepartment(@RequestBody Department department) {
        Integer id = departmentService.create(department);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/departments", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateDepartment(@RequestBody Department department) {
        Integer departmentId = departmentService.update(department);
        return departmentId > 0
                ? new ResponseEntity<>(departmentId, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/departments/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteDepartment(@PathVariable Integer id) {
        Integer departmentId = departmentService.delete(id);
        return departmentId > 0
                ? new ResponseEntity<>(departmentId, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
