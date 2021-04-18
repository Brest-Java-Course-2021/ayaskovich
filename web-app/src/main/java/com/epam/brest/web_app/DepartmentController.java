package com.epam.brest.web_app;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentDtoService;
import com.epam.brest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentDtoService departmentDtoService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentDtoService departmentDtoService) {
        this.departmentService = departmentService;
        this.departmentDtoService = departmentDtoService;
    }

    @GetMapping(value = "/departments")
    public String showAllDepartments(Model model) {
        model.addAttribute("departments", departmentDtoService.findAllWithAvgSalary());
        return "departments";
    }

    @GetMapping(value = "/department/{id}")
    public String updateDepartment(@PathVariable Integer id, Model model) {
        Optional<Department> optionalDepartment = departmentService.findById(id);
        if(optionalDepartment.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("department", optionalDepartment.get());
            return "department";
        } else {
            // ToDo department not found - show error message
            return "redirect:/departments";
        }
    }

    @PostMapping(value = "/department/{id}")
    public String updateDepartment(Department department) {
        departmentService.update(department);
        return "redirect:/departments";
    }

    @GetMapping(value = "/department")
    public String addNewDepartment(Model model) {
        model.addAttribute("isNew", true);
        model.addAttribute("department", new Department());
        return "department";
    }

    @PostMapping(value = "/department")
    public String addDepartment(Department department) {
        departmentService.create(department);
        return "redirect:/departments";
    }

    @GetMapping(value = "/department/{id}/delete")
    public String deleteDepartmentById(@PathVariable Integer id, Model model) {
        departmentService.delete(id);
        return "redirect:/departments";
    }
}
