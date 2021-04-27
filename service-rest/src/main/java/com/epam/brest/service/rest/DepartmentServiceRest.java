package com.epam.brest.service.rest;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceRest implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceRest.class);

    private String url;
    private RestTemplate restTemplate;

    public DepartmentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Gets all Departments from rest-app
     */
    @Override
    public List<Department> findAll() {
        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Department>) responseEntity.getBody();
    }

    /**
     * Sends departmentID to rest-app and gets Department by ID back
     */
    @Override
    public Optional<Department> findById(Integer departmentId) {
        LOGGER.debug("findById({})", departmentId);
        ResponseEntity<Department> responseEntity =
                restTemplate.getForEntity(url + "/" + departmentId, Department.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    /**
     * Gets new Department from rest-app
     */
    @Override
    public Integer create(Department department) {
        LOGGER.debug("create({})", department);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, department, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    /**
     * Sends departmentID to rest-app and gets update Department back
     */
    @Override
    public Integer update(Department department) {
        LOGGER.debug("update({})", department);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(department, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    /**
     * Delete Department
     */
    @Override
    public Integer delete(Integer departmentId) {
        LOGGER.debug("delete({})", departmentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + departmentId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    /**
     * Gets number of Departments from rest-app
     */
    @Override
    public Integer count() {
        return null;
    }
}
