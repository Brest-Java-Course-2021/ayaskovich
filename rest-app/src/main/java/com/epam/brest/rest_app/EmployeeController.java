package com.epam.brest.rest_app;

import com.epam.brest.model.Employee;
import com.epam.brest.model.dto.EmployeeDto;
import com.epam.brest.service.EmployeeDtoService;
import com.epam.brest.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeDtoService employeeDtoService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeDtoService employeeDtoService) {
        this.employeeService = employeeService;
        this.employeeDtoService = employeeDtoService;
    }

    @GetMapping(value = "/employees")
    public Collection<EmployeeDto> employees() {
        LOGGER.debug("employees()");
        return employeeDtoService.findAll();
    }

    @GetMapping(value = "/employees-by-date/{dates}") // 2018-01-01_2020-05-12
    public Collection<EmployeeDto> employeesByDate(@PathVariable String dates) {
        LOGGER.debug("employeesByDate({})", dates);
        String[] dateStringArray = dates.split("_");
        Date firstDate = Date.valueOf(dateStringArray[0]);
        Date secondDate = Date.valueOf(dateStringArray[1]);
        return employeeDtoService.findByDate(firstDate, secondDate);
    }

    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Integer id) {
        LOGGER.debug("findById({})", id);
        Optional<Employee> optional = employeeService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/employees", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createEmployee(@RequestBody Employee employee) {
        LOGGER.debug("createEmployee({})", employee);
        Integer id = employeeService.create(employee);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/employees", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateEmployee(@RequestBody Employee employee) {
        LOGGER.debug("updateEmployee({})", employee);
        Integer employeeId = employeeService.update(employee);
        return employeeId > 0
                ? new ResponseEntity<>(employeeId, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/employees/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteEmployee(@PathVariable Integer id) {
        LOGGER.debug("deleteEmployee({})", id);
        Integer employeeId = employeeService.delete(id);
        return employeeId > 0
                ? new ResponseEntity<>(employeeId, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
