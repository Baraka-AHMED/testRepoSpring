package com.exam.service;

import com.exam.model.Quiz;
import com.exam.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }

}