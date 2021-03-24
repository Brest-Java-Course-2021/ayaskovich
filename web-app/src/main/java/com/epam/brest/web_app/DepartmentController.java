package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DepartmentController {

    @GetMapping(value = "/departments")
    public String departments(Model model) {
        return "departments";
    }

    @GetMapping(value = "/department/{id}")
    public String goToEditDepartmentPAge(@PathVariable Integer id, Model model) {
        return "department";
    }

    @GetMapping(value = "/department/add")
    public String goToAddDepartmentPAge(Model model) {
        return "department";
    }
}
