package com.td.demo.controller;

import com.td.demo.model.Course;
import com.td.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	CourseService courseService;
	
    @GetMapping
    public Iterable<Course> findAllEmployee() {
        return this.courseService.findAllCourses();
    }

    @PostMapping("/add")
    public Course addCourse(@RequestParam String title) {
        return this.courseService.addCourse(title);
    }

}