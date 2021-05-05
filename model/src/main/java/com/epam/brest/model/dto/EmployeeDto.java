package com.epam.brest.model.dto;


import java.sql.Date;
import java.util.Objects;

public class EmployeeDto {

    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private String departmentName;
    private Date hired;

    public EmployeeDto() {}

    public EmployeeDto(String firstName, String lastName, String email, Double salary, String departmentName, Date hired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.departmentName = departmentName;
        this.hired = hired;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getHired() {
        return hired;
    }

    public void setHired(Date hired) {
        this.hired = hired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(employeeId, that.employeeId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(salary, that.salary) && Objects.equals(departmentName, that.departmentName) && Objects.equals(hired, that.hired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, email, salary, departmentName, hired);
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", departmentName='" + departmentName + '\'' +
                ", hired=" + hired +
                '}';
    }
}
