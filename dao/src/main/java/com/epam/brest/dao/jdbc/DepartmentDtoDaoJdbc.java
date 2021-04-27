package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.DepartmentDtoDao;
import com.epam.brest.model.dto.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentDtoDaoJdbc implements DepartmentDtoDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoDaoJdbc.class);

    private String findAllWithAvgSalarySql = "select d.department_id as departmentId, d.department_name as departmentName, " +
            " avg(e.salary) as avgSalary from department d left join employee e on d.department_id = e.department_id " +
            " group by d.department_id, d.department_name order by department_name";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Returns all Departments with average salaries
     */
    @Override
    public List<DepartmentDto> findAllWithAvgSalary() {
        LOGGER.debug("findAllWithAvgSalary()");
        List<DepartmentDto> departmentDtoList = namedParameterJdbcTemplate.query(findAllWithAvgSalarySql,
                BeanPropertyRowMapper.newInstance(DepartmentDto.class));
        return departmentDtoList;
    }
}
