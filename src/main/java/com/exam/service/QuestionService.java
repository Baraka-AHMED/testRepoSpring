package com.exam.service;

import com.exam.model.Exam;
import com.exam.model.Question;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.repository.ExamRepository;
import com.exam.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private ExamRepository examRepository;

    public List<Question> getAllQuestions() {
        return (List<Question>) questionRepository.findAll();
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void deleteQuestionById(Long id) {
        questionRepository.deleteById(id);
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id " + questionId));
    }

	public List<Question> findQuestionsByExamId(Long examId) {
		return questionRepository.findQuestionsByExamId(examId);
	}

	public void saveQuestion(Question question, Long examId) {
		
		Exam exam = examRepository.findExamById(examId)
				.orElseThrow(()-> new RuntimeException("Exam not found"));
		question.setExam(exam);
		questionRepository.save(question);
	}

}