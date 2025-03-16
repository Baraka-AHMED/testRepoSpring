package com.exam.service;

import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.repository.QuestionRepository;
import com.exam.repository.QuizRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
	
    @Autowired
    private QuizRepository quizRepository;

    @Autowired 
    private QuestionRepository questionRepository;
    
    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }
    
    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id " + quizId));
    }
    
    
    public Iterable<Question> getQuestionsForQuiz(Long quizId){
    
    	Quiz quiz = quizRepository.findById(quizId)
    			.orElseThrow(() -> new EntityNotFoundException("Quiz not found"));
    	
    	return quiz.getQuestions();
    }
    
    
    public Quiz addQuestionToQuiz(Long quizId, Long questionId) {
    	
    	Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
            
        if (quiz.getQuestions().contains(question)) {
                throw new IllegalArgumentException("This question is already associated with the quiz");
        }

        quiz.addQuestion(question);
        
        return quizRepository.save(quiz);
    }
    
    
    public Quiz removeQuestionFromQuiz(Long quizId, Long questionId) {
    	
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        if (!quiz.getQuestions().contains(question)) {
            throw new IllegalArgumentException("This question is not associated with the quiz");
        }

        quiz.getQuestions().remove(question);  
        
        return quizRepository.save(quiz);
    }
    
}