package com.td.demo.repository;

import com.td.demo.model.Course_students;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStudentsRepository extends JpaRepository<Course_students, Long> {
}
