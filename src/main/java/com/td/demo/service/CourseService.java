package com.td.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.demo.model.Course;
import com.td.demo.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public Course addCourse(String title) {
		Course course = new Course(title);
		return courseRepository.save(course);
	}
	
	public List<Course> findAllCourses(){
		return courseRepository.findAll();
	}
	
}
