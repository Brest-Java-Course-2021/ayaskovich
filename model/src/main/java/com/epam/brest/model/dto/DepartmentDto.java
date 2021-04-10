package com.epam.brest.model.dto;

import java.math.BigDecimal;

public class DepartmentDto {

    private Integer departmentId;
    private String departmentName;
    private BigDecimal avgSalary;

    public DepartmentDto() {
    }

    public DepartmentDto(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public BigDecimal getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(BigDecimal avgSalary) {
        this.avgSalary = avgSalary;
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", avgSalary=" + avgSalary +
                '}';
    }
}
