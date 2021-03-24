package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.model.Department;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DepartmentDaoJdbc implements DepartmentDao {

    @Value("${department.select}")
    private String findAllSql;

    @Value("${department.findById}")
    private String findByIdSql;

    @Value("${department.create}")
    private String createSql;

    @Value("${department.update}")
    private String updateSql;

    @Value("${department.check}")
    private String checkSql;

    @Value("${department.delete}")
    private String deleteSql;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Department.class);

    public DepartmentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> findAll() {
        return namedParameterJdbcTemplate.query(findAllSql, rowMapper);
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_ID", departmentId);
        return Optional.ofNullable((Department) namedParameterJdbcTemplate.query(findByIdSql, sqlParameterSource, rowMapper).get(0));
    }

    @Override
    public Integer create(Department department) {
        if(!isDepartmentNameUnique(department)) {
            throw new IllegalArgumentException("Department with the same name is already been in DB");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName());
        namedParameterJdbcTemplate.update(createSql, sqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    private boolean isDepartmentNameUnique(Department department) {
        return namedParameterJdbcTemplate.queryForObject(checkSql,
                new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName()), Integer.class) == 0;
    }

    @Override
    public Integer update(Department department) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName())
                        .addValue("DEPARTMENT_ID", department.getDepartmentId());
        return namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
    }

    @Override
    public Integer delete() {
        return null;
    }

}
