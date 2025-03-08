package com.exam.repository;
import com.exam.model.Exam;
import com.exam.model.Quiz;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<Quiz,Long>{}
