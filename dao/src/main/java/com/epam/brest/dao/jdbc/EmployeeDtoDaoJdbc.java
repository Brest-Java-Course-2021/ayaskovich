package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.EmployeeDtoDao;
import com.epam.brest.model.dto.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeDtoDaoJdbc implements EmployeeDtoDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoJdbc.class);

    private String findAllSql =
            "SELECT " +
                    "e.employee_id as employeeId, " +
                    "e.firstname as firstName, " +
                    "e.lastname as lastName, " +
                    "e.email as email, " +
                    "e.salary as salary, " +
                    "d.department_name as departmentName, " +
                    "e.hired as hired " +
                    "FROM employee AS e LEFT JOIN department AS d  " +
                    "ON e.department_id = d.department_id ORDER BY e.hired";
    private String findByDateSql = "SELECT " +
            "e.employee_id as employeeId, " +
            "e.firstname as firstName, " +
            "e.lastname as lastName, " +
            "e.email as email, " +
            "e.salary as salary, " +
            "d.department_name as departmentName, " +
            "e.hired as hired " +
            "FROM employee AS e LEFT JOIN department AS d  " +
            "ON e.department_id = d.department_id " +
            "WHERE E.HIRED >= :FIRST_DATE AND E.HIRED <= :SECOND_DATE ORDER BY e.HIRED";

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(EmployeeDto.class);

    public EmployeeDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Returns all Employees
     */
    @Override
    public List<EmployeeDto> findAll() {
        LOGGER.debug("findAll()");
        return namedParameterJdbcTemplate.query(findAllSql, new EmployeeDtoDaoJdbc.EmployeeDtoRowMapper());
    }

    /**
     * Returns all employees hired in the specified date period
     */
    @Override
    public List<EmployeeDto> findByDate(Date firstDate, Date secondDate) {
        LOGGER.debug("findAByDate({},{})", firstDate, secondDate);
        Map<String,Date> values = new HashMap<>();
        values.put("FIRST_DATE", firstDate);
        values.put("SECOND_DATE", secondDate);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(values);
        return namedParameterJdbcTemplate.query(findByDateSql, sqlParameterSource, new EmployeeDtoDaoJdbc.EmployeeDtoRowMapper());
    }


    private class EmployeeDtoRowMapper implements RowMapper<EmployeeDto>{

        @Override
        public EmployeeDto mapRow(ResultSet resultSet, int i) throws SQLException {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployeeId(resultSet.getInt("employeeId"));
            employeeDto.setFirstName(resultSet.getString("firstName"));
            employeeDto.setLastName(resultSet.getString("lastName"));
            employeeDto.setEmail(resultSet.getString("email"));
            employeeDto.setSalary(resultSet.getDouble("salary"));
            employeeDto.setDepartmentName(resultSet.getString("departmentName"));
            employeeDto.setHired(resultSet.getDate("hired"));
            return employeeDto;
        }
    }

}
