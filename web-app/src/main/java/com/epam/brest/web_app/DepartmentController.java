package com.epam.brest.web_app;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentDtoService;
import com.epam.brest.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;
    private final DepartmentDtoService departmentDtoService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentDtoService departmentDtoService) {
        this.departmentService = departmentService;
        this.departmentDtoService = departmentDtoService;
    }

    /**
     * Shows all Departments
     */
    @GetMapping(value = "/departments")
    public String showAllDepartments(Model model) {
        LOGGER.debug("showAllDepartments()");
        model.addAttribute("departments", departmentDtoService.findAllWithAvgSalary());
        return "departments";
    }

    /**
     * Shows form to edit with Department's values
     */
    @GetMapping(value = "/department/{id}")
    public String updateDepartment(@PathVariable Integer id, Model model) {
        LOGGER.debug("updateDepartment({},{})", id, model);
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

    /**
     * Sends completed form and makes redirect to Departments
     */
    @PostMapping(value = "/department/{id}")
    public String updateDepartment(Department department) {
        LOGGER.debug("updateDepartment({}, {})", department);
        departmentService.update(department);
        return "redirect:/departments";
    }

    /**
     * Shows empty form to create new Department
     */
    @GetMapping(value = "/department")
    public String addNewDepartment(Model model) {
        LOGGER.debug("addNewDepartment({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("department", new Department());
        return "department";
    }

    /**
     * Sends completed form and makes redirect to Departments
     */
    @PostMapping(value = "/department")
    public String addDepartment(Department department) {
        LOGGER.debug("updateDepartment({}, {})", department);
        departmentService.create(department);
        return "redirect:/departments";
    }

    /**
     * Sends Department's ID to delete and makes redirect to Departments
     */
    @GetMapping(value = "/department/{id}/delete")
    public String deleteDepartmentById(@PathVariable Integer id, Model model) {
        LOGGER.debug("deleteDepartmentById({},{})", id, model);
        departmentService.delete(id);
        return "redirect:/departments";
    }
}
