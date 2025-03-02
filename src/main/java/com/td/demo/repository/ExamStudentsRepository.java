package com.td.demo.repository;

import com.td.demo.model.Exam_students;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamStudentsRepository extends JpaRepository<Exam_students, Long> {
}
