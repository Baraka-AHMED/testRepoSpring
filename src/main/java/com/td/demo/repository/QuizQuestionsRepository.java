package com.td.demo.repository;

import com.td.demo.model.Quiz_questions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionsRepository extends JpaRepository<Quiz_questions, Long> {
}
