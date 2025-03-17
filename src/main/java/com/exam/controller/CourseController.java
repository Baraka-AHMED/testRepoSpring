package com.exam.controller;


import com.exam.model.Course;
import com.exam.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public List<Course> findAll(){
        return courseService.getAllCourses();
    }

    @GetMapping("/find")
    public Optional<Course> findById(@RequestParam Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/add")
    public void add(@RequestBody Course course){
        courseService.addCourse(course);
    }

    @PutMapping("/update")
    public void update(@RequestBody Course course) {
        courseService.updateCourse(course);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam Long id){
        courseService.deleteCourseById(id);
    }
}