package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class EmployeeDaoJdbc implements EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoJdbc.class);

    private String findAllSql =
            "SELECT * FROM EMPLOYEE AS E ORDER BY E.EMPLOYEE_ID";
    private String findByDateSql =
            "SELECT * FROM EMPLOYEE AS E " +
                    "WHERE E.HARED >= :FIRST_DATE AND E.HARED <= :SECOND_DATE ORDER BY E.HARED";
    private String findByIdSql =
            "SELECT * FROM EMPLOYEE AS E WHERE E.EMPLOYEE_ID = :EMPLOYEE_ID";
    private String createSql =
            "INSERT INTO EMPLOYEE (FIRSTNAME, LASTNAME, EMAIL, SALARY, DEPARTMENT_ID, HARED) " +
                    "VALUES (:FIRSTNAME, :LASTNAME, :EMAIL, :SALARY, :DEPARTMENT_ID, :HARED)";
    private String updateSql = "UPDATE EMPLOYEE SET " +
                    "FIRSTNAME = :FIRSTNAME, LASTNAME = :LASTNAME, EMAIL = :EMAIL, SALARY = :SALARY, " +
            "DEPARTMENT_ID = :DEPARTMENT_ID, HARED = :HARED " +
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

    /**
     * Returns all Employees
     */
    @Override
    public List<Employee> findAll() {
        LOGGER.debug("findAll()");
        return namedParameterJdbcTemplate.query(findAllSql, new EmployeeRowMapper());
    }

    /**
     * Returns all employees hired in the specified date period
     */
    @Override
    public List<Employee> findAByDate(Date firstDate, Date secondDate) {
        LOGGER.debug("findAByDate({},{})", firstDate, secondDate);
        Map<String,Date> values = new HashMap<>();
        values.put("FIRST_DATE", firstDate);
        values.put("SECOND_DATE", secondDate);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(values);
        return namedParameterJdbcTemplate.query(findByDateSql, sqlParameterSource, new EmployeeRowMapper());
    }

    /**
     * Return Employee by ID
     */
    @Override
    public Optional<Employee> findById(Integer employeeId) {
        LOGGER.debug("findById({})", employeeId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("EMPLOYEE_ID", employeeId);
        // Note: don't use queryForObject to reduce exception handling
        List<Employee> results = namedParameterJdbcTemplate.query(findByIdSql, sqlParameterSource, new EmployeeRowMapper());
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    /**
     * Create new Employee
     */
    @Override
    public Integer create(Employee employee) {
        LOGGER.debug("create({})", employee);
        if(!isEmployeeEmailUnique(employee)) {
            throw new IllegalArgumentException("Employee with the same email is already been in DB");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map sqlParameter = new HashMap();
        sqlParameter.put("FIRSTNAME", employee.getFirstName());
        sqlParameter.put("LASTNAME", employee.getLastName());
        sqlParameter.put("EMAIL", employee.getEmail());
        sqlParameter.put("SALARY", employee.getSalary());
        sqlParameter.put("DEPARTMENT_ID", employee.getDepartmentId());
        sqlParameter.put("HARED", new Date(Calendar.getInstance().getTime().getTime()));
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(sqlParameter);
        namedParameterJdbcTemplate.update(createSql, sqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    /**
     * Check the Employee email for uniqueness
     */
    private boolean isEmployeeEmailUnique(Employee employee) {
        LOGGER.debug("isEmployeeEmailUnique({})", employee);
        return namedParameterJdbcTemplate.queryForObject(checkSql,
                new MapSqlParameterSource("EMAIL", employee.getEmail()), Integer.class) == 0;
    }

    /**
     * Edit Department
     */
    @Override
    public Integer update(Employee employee) {
        LOGGER.debug("update({})", employee);
        Map sqlParameter = new HashMap();
        sqlParameter.put("EMPLOYEE_ID", employee.getEmployeeId());
        sqlParameter.put("FIRSTNAME", employee.getFirstName());
        sqlParameter.put("LASTNAME", employee.getLastName());
        sqlParameter.put("EMAIL", employee.getEmail());
        sqlParameter.put("SALARY", employee.getSalary());
        sqlParameter.put("DEPARTMENT_ID", employee.getDepartmentId());
        sqlParameter.put("HARED", employee.getHared() );
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(sqlParameter);
        return namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
    }

    /**
     * Delete Employee
     */
    @Override
    public Integer delete(Integer employeeId) {
        LOGGER.debug("delete({})", employeeId);
        return namedParameterJdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("EMPLOYEE_ID", employeeId));
    }

    private class EmployeeRowMapper implements RowMapper<Employee>{

        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setEmployeeId(resultSet.getInt("EMPLOYEE_ID"));
            employee.setFirstName(resultSet.getString("FIRSTNAME"));
            employee.setLastName(resultSet.getString("LASTNAME"));
            employee.setEmail(resultSet.getString("EMAIL"));
            employee.setSalary(resultSet.getDouble("SALARY"));
            employee.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
            employee.setHared(resultSet.getDate("HARED"));
            return employee;
        }
    }
}
