package com.epam.brest.web_app;

import com.epam.brest.model.Department;
import com.epam.brest.model.dto.DepartmentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class DepartmentControllerIT {

    private static final String DEPARTMENT_DTOS_URL = "http://localhost:8088/department-dtos";
    private static final String DEPARTMENTS_URL = "http://localhost:8088/departments";
    public static final int DEPARTMENT_NAME_SIZE = 50;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldReturnDepartmentsPage() throws Exception {
        DepartmentDto d1 = createDepartmentDto(1, "IT", BigDecimal.valueOf(150));
        DepartmentDto d2 = createDepartmentDto(2, "SECURITY", BigDecimal.valueOf(400));
        DepartmentDto d3 = createDepartmentDto(3, "MANAGEMENT", null);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENT_DTOS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(d1, d2, d3)))
                );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("departments"))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(d1.getDepartmentId())),
                                hasProperty("departmentName", is(d1.getDepartmentName())),
                                hasProperty("avgSalary", is(d1.getAvgSalary()))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(d2.getDepartmentId())),
                                hasProperty("departmentName", is(d2.getDepartmentName())),
                                hasProperty("avgSalary", is(d2.getAvgSalary()))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(d3.getDepartmentId())),
                                hasProperty("departmentName", is(d3.getDepartmentName())),
                                hasProperty("avgSalary", isEmptyOrNullString())
                        )
                )))
        ;
        mockServer.verify();
    }

    @Test
    public void shouldOpenNewDepartmentPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("department", isA(Department.class)));
        mockServer.verify();
    }

    @Test
    public void shouldAddNewDepartment() throws Exception {

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentName", "test")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));

        mockServer.verify();
    }

    @Test
    public void shouldOpenEditDepartmentPageById() throws Exception {
        Department d = createDepartment(1, "IT");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + d.getDepartmentId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(d))
                );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("department", hasProperty("departmentId", is(d.getDepartmentId()))))
                .andExpect(model().attribute("department", hasProperty("departmentName", is(d.getDepartmentName()))));
        mockServer.verify();
    }

    @Test
    public void shouldReturnToDepartmentsPageIfDepartmentNotFoundById() throws Exception {
        int id = 99999;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/" + id)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/departments"));
        mockServer.verify();
    }

    @Test
    public void shouldUpdateDepartmentAfterEdit() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        String testName = RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/department/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentId", "1")
                        .param("departmentName", testName)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));

        mockServer.verify();
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {
        int id = 3;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));

        mockServer.verify();
    }

    private DepartmentDto createDepartmentDto(int id, String name, BigDecimal avgSalary) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(id);
        departmentDto.setDepartmentName(name);
        departmentDto.setAvgSalary(avgSalary);
        return departmentDto;
    }

    private Department createDepartment(int id, String name) {
        Department department = new Department();
        department.setDepartmentId(id);
        department.setDepartmentName(name);
        return department;
    }
}
