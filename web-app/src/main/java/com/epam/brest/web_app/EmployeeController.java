package com.epam.brest.web_app;

import com.epam.brest.model.Employee;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/employees")
    public String showAllDepartments(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees";
    }

    @GetMapping(value = "/employee")
    public String addNewEmployee(Model model) {
        model.addAttribute("isNew", true);
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        return "employee";
    }

    @PostMapping(value = "/employee")
    public String addDepartment(Employee employee) {
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/employee/{id}")
    public String updateEmployee(@PathVariable Integer id, Model model) {
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

    @PostMapping(value = "/employee/{id}")
    public String updateDepartment(Employee employee) {
        employeeService.update(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/employee/{id}/delete")
    public String deleteEmployeeById(@PathVariable Integer id, Model model) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
