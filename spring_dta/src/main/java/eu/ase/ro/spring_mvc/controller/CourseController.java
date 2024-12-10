package eu.ase.ro.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {
    @GetMapping("/courses")
    public String coursePage() {
        return "courses/index";
    }
}
