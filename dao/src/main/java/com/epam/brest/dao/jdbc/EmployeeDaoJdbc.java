package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Department;
import com.epam.brest.model.Employee;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmployeeDaoJdbc implements EmployeeDao {

    private String findAllSql =
            "SELECT E.EMPLOYEE_ID, E.FIRSTNAME, E.LASTNAME, E.EMAIL, E.SALARY, E.DEPARTMENT_ID FROM EMPLOYEE AS E ORDER BY E.EMPLOYEE_ID";
    private String findByIdSql =
            "SELECT * FROM EMPLOYEE AS E WHERE E.EMPLOYEE_ID = :EMPLOYEE_ID";
    private String createSql =
            "INSERT INTO EMPLOYEE (FIRSTNAME, LASTNAME, EMAIL, SALARY, DEPARTMENT_ID) " +
                    "VALUES (:FIRSTNAME, :LASTNAME, :EMAIL, :SALARY, :DEPARTMENT_ID)";
    private String updateSql = "UPDATE EMPLOYEE SET " +
                    "FIRSTNAME = :FIRSTNAME, LASTNAME = :LASTNAME, EMAIL = :EMAIL, SALARY = :SALARY, DEPARTMENT_ID = :DEPARTMENT_ID " +
                        "WHERE EMPLOYEE_ID = :EMPLOYEE_ID";
    private String deleteSql =
            "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = :EMPLOYEE_ID";
    private String checkSql =
            "SELECT COUNT(EMPLOYEE_ID) FROM EMPLOYEE WHERE lower(EMAIL) = lower(:EMAIL)";


    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Employee.class);

    public EmployeeDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        return namedParameterJdbcTemplate.query(findAllSql, rowMapper);
    }

    @Override
    public Optional<Employee> findById(Integer employeeId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("EMPLOYEE_ID", employeeId);
        // Note: don't use queryForObject to reduce exception handling
        List<Employee> results = namedParameterJdbcTemplate.query(findByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    @Override
    public Integer create(Employee employee) {
        if(!isDepartmentNameUnique(employee)) {
            throw new IllegalArgumentException("Employee with the same email is already been in DB");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map sqlParameter = new HashMap();
        sqlParameter.put("FIRSTNAME", employee.getFirstName());
        sqlParameter.put("LASTNAME", employee.getLastName());
        sqlParameter.put("EMAIL", employee.getEmail());
        sqlParameter.put("SALARY", employee.getSalary());
        sqlParameter.put("DEPARTMENT_ID", employee.getDepartmentId());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(sqlParameter);
        namedParameterJdbcTemplate.update(createSql, sqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    private boolean isDepartmentNameUnique(Employee employee) {
        return namedParameterJdbcTemplate.queryForObject(checkSql,
                new MapSqlParameterSource("EMAIL", employee.getEmail()), Integer.class) == 0;
    }

    @Override
    public Integer update(Employee employee) {
        Map sqlParameter = new HashMap();
        sqlParameter.put("EMPLOYEE_ID", employee.getEmployeeId());
        sqlParameter.put("FIRSTNAME", employee.getFirstName());
        sqlParameter.put("LASTNAME", employee.getLastName());
        sqlParameter.put("EMAIL", employee.getEmail());
        sqlParameter.put("SALARY", employee.getSalary());
        sqlParameter.put("DEPARTMENT_ID", employee.getDepartmentId());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(sqlParameter);
        return namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer employeeId) {
        return namedParameterJdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("EMPLOYEE_ID", employeeId));
    }


}
