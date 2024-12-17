package eu.ase.ro.spring.mvc.controller;

import eu.ase.ro.spring.mvc.reponse.CourseResponse;
import eu.ase.ro.spring.mvc.request.CourseRequest;
import eu.ase.ro.spring.mvc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseController {
    public static final String COURSES = "courses";
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String coursePage(Model model) {
        List<CourseResponse> courses = courseService.getAll();
        model.addAttribute(COURSES, courses);
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

    @PostMapping("/courses")
    public String save(@ModelAttribute CourseRequest request,
                       @RequestParam(required = false) Integer courseId) {
        courseService.save(request);
        return "redirect:/home";
    }

    @GetMapping("/courses/{courseId}/edit")
    public String navigateToEditPage(@PathVariable Integer courseId, Model model) {
        CourseResponse course = courseService.findById(courseId);
        model.addAttribute("course", course);
        return "courses/add";
    }
}
