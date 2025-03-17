package com.exam.controller;

import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.model.Exam;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;  // Service pour accéder aux quiz

    @Autowired
    private ExamService examService;  // Service pour accéder aux examens

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("/add")
    public void addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
    }

    @DeleteMapping("/deleteById")
    public void deleteQuestionById(@RequestParam Long id) {
        questionService.deleteQuestionById(id);
    }

    /*
    // Ajouter une question à un examen
    @PostMapping("/addToExam")
    public void addQuestionToExam(@RequestParam Long questionId, @RequestParam Long examId) {
        Question question = questionService.getQuestionById(questionId);
        Exam exam = examService.getExamById(examId);

        if (question != null && exam != null) {
            exam.getQuestions().add(question);  // Ajoute la question à l'examen
            question.setExam(exam);  // Associe l'examen à la question
            examService.addExam(exam);  // Sauvegarde de l'examen mis à jour
            questionService.addQuestion(question);  // Sauvegarde de la question mise à jour
        } else {
            throw new RuntimeException("Question or Exam not found");
        }
    }
    */
}
