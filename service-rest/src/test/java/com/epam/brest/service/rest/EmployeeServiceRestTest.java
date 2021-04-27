package com.epam.brest.service.rest;

import com.epam.brest.model.Department;
import com.epam.brest.model.Employee;
import com.epam.brest.service.EmployeeService;
import com.epam.brest.service.rest.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.service.rest.config.TestConfig.EMPLOYEES_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class EmployeeServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceRestTest.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmployeeService employeeService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldFindAllEmployees() throws Exception {
        LOGGER.debug("shouldFindAllEmployees()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(EMPLOYEES_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                createEmployee(0),
                                createEmployee(1))))
                );

        // when
        List<Employee> employees = employeeService.findAll();

        // then
        mockServer.verify();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        LOGGER.debug("shouldCreateEmployee()");
        // given
        Employee employee = createEmployee(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(EMPLOYEES_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = employeeService.create(employee);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    public void shouldFindEmployeeById() throws Exception {
        LOGGER.debug("shouldFindEmployeeById()");
        // given
        Integer id = 1;
        Employee employee = createEmployee(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(EMPLOYEES_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee))
                );

        // when
        Optional<Employee> optionalEmployee = employeeService.findById(id);

        // then
        mockServer.verify();
        assertTrue(optionalEmployee.isPresent());
        assertEquals(optionalEmployee.get().getDepartmentId(), id);
        assertEquals(optionalEmployee.get().getFirstName(), employee.getFirstName());
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        LOGGER.debug("shouldUpdateEmployee()");
        // given
        Integer id = 1;
        Employee employee = createEmployee(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(EMPLOYEES_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(EMPLOYEES_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee))
                );

        // when
        int result = employeeService.update(employee);
        Optional<Employee> updatedEmployeeOptional = employeeService.findById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedEmployeeOptional.isPresent());
        assertEquals(updatedEmployeeOptional.get().getDepartmentId(), id);
        assertEquals(updatedEmployeeOptional.get().getFirstName(), employee.getFirstName());
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {
        LOGGER.debug("shouldDeleteEmployee()");
        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(EMPLOYEES_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = employeeService.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }


    private Employee createEmployee(int index) {
        Employee employee = new Employee();
        employee.setDepartmentId(index);
        employee.setFirstName(RandomStringUtils.randomAlphabetic(50));
        return employee;
    }
}
