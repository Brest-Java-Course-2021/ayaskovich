package com.epam.brest.service.rest;

import com.epam.brest.model.Employee;
import com.epam.brest.model.dto.EmployeeDto;
import com.epam.brest.service.EmployeeDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.List;

@Service
public class EmployeeDtoServiceRest implements EmployeeDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDtoServiceRest.class);

    private String url;
    private RestTemplate restTemplate;

    public EmployeeDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Gets all Employees from rest-app
     */
    @Override
    public List<EmployeeDto> findAll() {
        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8088/employees", List.class);
        return (List<EmployeeDto>) responseEntity.getBody();
    }

    /**
     * Sends date period to rest-app and gets Employees hired in this period
     */
    @Override
    public List<EmployeeDto> findByDate(Date firstDate, Date secondDate) {
        LOGGER.debug("findByDate({},{})", firstDate, secondDate);
        String dates = firstDate.toString()+"_"+secondDate.toString();
        ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8088/employees-by-date/"+dates, List.class);
        return (List<EmployeeDto>) responseEntity.getBody();
    }
}
