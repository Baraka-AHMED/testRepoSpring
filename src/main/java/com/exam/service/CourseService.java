package com.exam.service;

import com.exam.model.Course;
import com.exam.model.User;
import com.exam.repository.CourseRepository;
import com.exam.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Course> getAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        if (courseRepository.existsById(course.getId())) {
            courseRepository.save(course);
        }
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

	public List<Course> findCoursesByTeacherId(Long teacherId) {
		return courseRepository.findCoursesByTeacherId(teacherId);
	}

	@Transactional
	public void enrollStudentInCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        course.getStudents().add(student);
        student.getCourses().add(course);

        courseRepository.save(course);
        userRepository.save(student);
    }
	
	@Transactional
    public void unenrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        course.getStudents().remove(student);
        student.getCourses().remove(course);

        courseRepository.save(course);  
        userRepository.save(student);  
    }

	
}