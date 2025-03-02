package com.td.demo.repository;

import com.td.demo.model.Exam;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
