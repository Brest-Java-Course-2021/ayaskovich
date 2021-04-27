package com.epam.brest.service.impl;

import com.epam.brest.dao.jdbc.DepartmentDtoDaoJdbc;
import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.DepartmentDtoService;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({DepartmentDtoServiceImpl.class, DepartmentDtoDaoJdbc.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@ComponentScan(basePackages = {"com.epam.brest.dao", "com.epam.brest.testdb"})
@Transactional
class DepartmentDtoServiceImplIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoServiceImplIT.class);

    @Autowired
    DepartmentDtoService departmentDtoService;

    @Test
    void findAllWithAvgSalary() {
        LOGGER.debug("findAllWithAvgSalary()");
        List<DepartmentDto> departmentDtoList = departmentDtoService.findAllWithAvgSalary();
        assertNotNull(departmentDtoList);
        assertTrue(departmentDtoList.size() > 0);
        assertTrue(departmentDtoList.get(0).getAvgSalary().intValue() > 0);
    }
}