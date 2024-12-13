package eu.ase.ro.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String coursePage() {
        return "courses/index";
    }

    @GetMapping("/courses/{courseId}/students/enroll")
    public String navigateToEnrollPage(@PathVariable Integer courseId) {
        System.out.println("course id: " + courseId);
        return "courses/enroll";
    }

    @PostMapping("/courses/{courseId}/students/enroll")
    public String enroll(@PathVariable Integer courseId) {
        System.out.println("Student has been enrolled to course " + courseId);
        return "redirect:/";
    }

    @GetMapping("/courses/add")
    public String navigateToAddPage() {
        return "courses/add";
    }
}
