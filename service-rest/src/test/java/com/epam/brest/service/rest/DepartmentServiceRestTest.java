package com.epam.brest.service.rest;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
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

import static com.epam.brest.service.rest.config.TestConfig.DEPARTMENTS_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class DepartmentServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceRestTest.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DepartmentService departmentService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldFindAllDepartments() throws Exception {
        LOGGER.debug("shouldFindAllDepartments()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                createDepartment(0),
                                createDepartment(1))))
                );

        // when
        List<Department> departments = departmentService.findAll();

        // then
        mockServer.verify();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    public void shouldCreateDepartment() throws Exception {
        LOGGER.debug("shouldCreateDepartment()");
        // given
        Department department = new Department(RandomStringUtils.randomAlphabetic(50));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = departmentService.create(department);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    public void shouldFindDepartmentById() throws Exception {
        LOGGER.debug("shouldFindDepartmentById()");
        // given
        Integer id = 1;
        Department department = new Department(RandomStringUtils.randomAlphabetic(50));
        department.setDepartmentId(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(department))
                );

        // when
        Optional<Department> optionalDepartment = departmentService.findById(id);

        // then
        mockServer.verify();
        assertTrue(optionalDepartment.isPresent());
        assertEquals(optionalDepartment.get().getDepartmentId(), id);
        assertEquals(optionalDepartment.get().getDepartmentName(), department.getDepartmentName());
    }

    @Test
    public void shouldUpdateDepartment() throws Exception {
        LOGGER.debug("shouldUpdateDepartment()");
        // given
        Integer id = 1;
        Department department = new Department(RandomStringUtils.randomAlphabetic(50));
        department.setDepartmentId(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(department))
                );

        // when
        int result = departmentService.update(department);
        Optional<Department> updatedDepartmentOptional = departmentService.findById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedDepartmentOptional.isPresent());
        assertEquals(updatedDepartmentOptional.get().getDepartmentId(), id);
        assertEquals(updatedDepartmentOptional.get().getDepartmentName(), department.getDepartmentName());
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {
        LOGGER.debug("shouldDeleteDepartment()");
        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = departmentService.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }

    private Department createDepartment(int index) {
        Department department = new Department();
        department.setDepartmentId(index);
        department.setDepartmentName("d" + index);
        return department;
    }
}
