package com.epam.brest.web_app;

import com.epam.brest.model.Dates;
import com.epam.brest.model.Employee;
import com.epam.brest.model.dto.EmployeeDto;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.EmployeeDtoService;
import com.epam.brest.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeDtoService employeeDtoService;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeDtoService employeeDtoService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.employeeDtoService = employeeDtoService;
        this.departmentService = departmentService;
    }

    /**
     * Shows all Employees
     */
    @GetMapping(value = "/employees")
    public String showAllEmployees(Model model) {
        LOGGER.debug("showAllEmployees()");
        Dates dates = new Dates();
        model.addAttribute(dates);
        model.addAttribute("employees", employeeDtoService.findAll());
        return "employees";
    }

    /**
     * Sends date period and shows all Employees hired in the specified date period
     */
    @PostMapping(value = "/employees")
    public String showEmployeesByDate(Dates dates, Model model) {
        LOGGER.debug("showEmployeesByDate({},{})", dates, model);
        Date firstDate = Date.valueOf(dates.getFirstDate());
        Date secondDate = Date.valueOf(dates.getSecondDate());
        List<EmployeeDto> employees = employeeDtoService.findByDate(firstDate, secondDate);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Shows empty form to create new Employee
     */
    @GetMapping(value = "/employee")
    public String addNewEmployee(Model model) {
        LOGGER.debug("addNewEmployee({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        return "employee";
    }

    /**
     * Sends completed form and makes redirect to Employeess
     */
    @PostMapping(value = "/employee")
    public String addEmployee(Employee employee) {
        LOGGER.debug("addEmployee({})", employee);
        employeeService.create(employee);
        return "redirect:/employees";
    }

    /**
     * Shows form to edit with Employee's values
     */
    @GetMapping(value = "/employee/{id}")
    public String updateEmployee(@PathVariable Integer id, Model model) {
        LOGGER.debug("updateEmployee({},{})", id, model);
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        if(optionalEmployee.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("employee", optionalEmployee.get());
            model.addAttribute("departments", departmentService.findAll());
            return "employee";
        } else {
            // ToDo department not found - show error message
            return "redirect:/employees";
        }
    }

    /**
     * Sends completed form and makes redirect to Employeess
     */
    @PostMapping(value = "/employee/{id}")
    public String updateEmployee(Employee employee) {
        LOGGER.debug("updateEmployee({})", employee);
        employeeService.update(employee);
        return "redirect:/employees";
    }

    /**
     * Sends Employee's ID to delete and makes redirect to Employees
     */
    @GetMapping(value = "/employee/{id}/delete")
    public String deleteEmployeeById(@PathVariable Integer id, Model model) {
        LOGGER.debug("deleteEmployeeById({},{})", id, model);
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
