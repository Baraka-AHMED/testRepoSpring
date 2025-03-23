package com.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.model.Question;
import com.exam.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

	@Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // Récupérer toutes les questions d'un examen donné
    public List<Question> getQuestionsByExam(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    // Récupérer une question par son ID
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findQuestionById(id);
    }

    // Créer une nouvelle question
    @Transactional
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Supprimer une question
    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found with ID: " + id);
        }
        questionRepository.deleteById(id);
    }

	public void save(Question question) {
		questionRepository.save(question);
	}
}

