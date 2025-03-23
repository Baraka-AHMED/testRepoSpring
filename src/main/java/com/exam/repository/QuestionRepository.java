package com.exam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
		
	List<Question> findByExamId(Long examId);

	Optional<Question> findQuestionById(Long id);

}
