package com.example.courseManagement.controller;

import com.example.courseManagement.model.Course;
import com.example.courseManagement.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ✅ Updated method with sorting
    @GetMapping("/courses")
    public String getAllCourses(@RequestParam(required = false, defaultValue = "id") String sortBy,
                                @RequestParam(required = false, defaultValue = "asc") String order,
                                Model model) {
        List<Course> courses = courseService.getAllCoursesSorted(sortBy, order);
        model.addAttribute("courses", courses);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        return "courses";
    }

    @GetMapping("/course/{id}")
    public String getCourseById(@PathVariable Long id, Model model) {
        Optional<Course> course = courseService.getCourseById(id);
        course.ifPresent(value -> model.addAttribute("course", value));
        return "course-detail";
    }

    @GetMapping("/courses/instructor/{name}")
    public String getCoursesByInstructor(@PathVariable String name, Model model) {
        List<Course> courses = courseService.getCoursesByInstructor(name);
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/course/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "course-form";
    }

    @PostMapping("/course")
    public String createCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        courseService.saveCourse(course);
        redirectAttributes.addFlashAttribute("message", "Course created successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/course/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            return "course-form";
        }
        return "redirect:/courses";
    }

    @PostMapping("/course/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        course.setId(id);
        courseService.saveCourse(course);
        redirectAttributes.addFlashAttribute("message", "Course updated successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/course/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("message", "Course deleted successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/courses/search")
    public String searchCourses(@RequestParam String query, @RequestParam String type, Model model) {
        if (type.equals("instructor")) {
            List<Course> courses = courseService.getCoursesByInstructor(query);
            model.addAttribute("courses", courses);
        } else if (type.equals("id")) {
            try {
                Long id = Long.parseLong(query);
                Optional<Course> course = courseService.getCourseById(id);
                model.addAttribute("courses", course.map(List::of).orElse(List.of()));
            } catch (NumberFormatException e) {
                model.addAttribute("courses", List.of());
            }
        } else {
            model.addAttribute("courses", List.of());
        }
        model.addAttribute("sortBy", "id");
        model.addAttribute("order", "asc");
        return "courses";
    }
}
