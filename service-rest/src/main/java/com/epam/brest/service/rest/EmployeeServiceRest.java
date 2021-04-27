package com.epam.brest.service.rest;

import com.epam.brest.model.Employee;
import com.epam.brest.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceRest implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceRest.class);

    private String url;
    private RestTemplate restTemplate;

    public EmployeeServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Gets all Employees from rest-app
     */
    @Override
    public List<Employee> findAll() {
        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8088/employees", List.class);
        return (List<Employee>) responseEntity.getBody();
    }

    /**
     * Sends date period to rest-app and gets Employees hired in this period
     */
    @Override
    public List<Employee> findByDate(Date firstDate, Date secondDate) {
        LOGGER.debug("findByDate({},{})", firstDate, secondDate);
        String dates = firstDate.toString()+"_"+secondDate.toString();
        ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8088/employees-by-date/"+dates, List.class);
        return (List<Employee>) responseEntity.getBody();
    }

    /**
     * Sends employeeID to rest-app and gets Employee by ID back
     */
    @Override
    public Optional<Employee> findById(Integer employeeId) {
        LOGGER.debug("findById({})", employeeId);
        ResponseEntity<Employee> responseEntity =
                restTemplate.getForEntity(url + "/" + employeeId, Employee.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    /**
     * Gets new Employee from rest-app
     */
    @Override
    public Integer create(Employee employee) {
        LOGGER.debug("create({})", employee);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, employee, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    /**
     * Sends employeeID to rest-app and gets update Employee back
     */
    @Override
    public Integer update(Employee employee) {
        LOGGER.debug("update({})", employee);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    /**
     * Delete Employee
     */
    @Override
    public Integer delete(Integer employeeId) {
        LOGGER.debug("delete({})", employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + employeeId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }
}
