package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *  Implementation of DepartmentDao interface.
 */
@Component
public class DepartmentDaoJdbc implements DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoJdbc.class);

    private String findAllSql =
            "SELECT D.DEPARTMENT_ID, D.DEPARTMENT_NAME FROM DEPARTMENT AS D ORDER BY D.DEPARTMENT_NAME";
    private String findByIdSql =
            "SELECT D.DEPARTMENT_ID, D.DEPARTMENT_NAME FROM DEPARTMENT AS D WHERE D.DEPARTMENT_ID = :DEPARTMENT_ID";
    private String createSql =
            "INSERT INTO DEPARTMENT (DEPARTMENT_NAME) VALUES (:DEPARTMENT_NAME)";
    private String updateSql =
            "UPDATE DEPARTMENT SET DEPARTMENT_NAME = :DEPARTMENT_NAME WHERE DEPARTMENT_ID = :DEPARTMENT_ID";
    private String checkSql =
            "SELECT COUNT(DEPARTMENT_ID) FROM DEPARTMENT WHERE lower(DEPARTMENT_NAME) = lower(:DEPARTMENT_NAME)";
    private String deleteSql =
            "DELETE FROM DEPARTMENT WHERE DEPARTMENT_ID = :DEPARTMENT_ID";
    private String countSql =
            "SELECT COUNT(*) FROM DEPARTMENT";

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Department.class);

    public DepartmentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Returns all Departments
     */
    @Override
    public List<Department> findAll() {
        LOGGER.debug("findAll()");
        return namedParameterJdbcTemplate.query(findAllSql, rowMapper);
    }

    /**
     * Return Department by ID
     */
    @Override
    public Optional<Department> findById(Integer departmentId) {
        LOGGER.debug("findById({})", departmentId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_ID", departmentId);
        // Note: don't use queryForObject to reduce exception handling
        List<Department> results = namedParameterJdbcTemplate.query(findByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    /**
     * Create new Department
     */
    @Override
    public Integer create(Department department) {
        LOGGER.debug("create({})", department);
        if(!isDepartmentNameUnique(department)) {
            throw new IllegalArgumentException("Department with the same name is already been in DB");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName());
        namedParameterJdbcTemplate.update(createSql, sqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    /**
     * Check the Department name for uniqueness
     */
    private boolean isDepartmentNameUnique(Department department) {
        LOGGER.debug("isDepartmentNameUnique({})", department);
        return namedParameterJdbcTemplate.queryForObject(checkSql,
                new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName()), Integer.class) == 0;
    }

    /**
     * Edit Department
     */
    @Override
    public Integer update(Department department) {
        LOGGER.debug("isDepartmentNameUnique({})", department);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName())
                        .addValue("DEPARTMENT_ID", department.getDepartmentId());
        return namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
    }

    /**
     * Delete Department
     */
    @Override
    public Integer delete(Integer departmentId) {
        LOGGER.debug("delete({})", departmentId);
        return namedParameterJdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("DEPARTMENT_ID", departmentId));
    }

    /**
     * Count Departments
     */
    @Override
    public Integer count() {
        return namedParameterJdbcTemplate.queryForObject(countSql, new HashMap<>(), Integer.class);
    }

}
