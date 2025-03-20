package com.exam.service;

import com.exam.model.Course;
import com.exam.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

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

	public void enrollStudentInCourse(Long studentId, Long courseId) {
		courseRepository.enrollStudentInCourse(studentId, courseId);
	}
}