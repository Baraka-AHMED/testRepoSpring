package com.exam.controller;

import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.service.QuizService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/all")
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/add")
    public void addQuiz(@RequestBody Quiz quiz) {
        quizService.saveQuiz(quiz);
    }

    @DeleteMapping("/deleteById")
    public void deleteQuizById(@RequestParam Long id) {
        quizService.deleteQuizById(id);
    }
    
    /*
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<Iterable<Question>> getQuestionsForQuiz(@PathVariable Long quizId) {
    	
        try {
            Iterable<Question> questions = quizService.getQuestionsForQuiz(quizId);
            return ResponseEntity.ok(questions); 
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
    }
    */
    
    
    /*
    @PostMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<Quiz> addQuestionToQuiz(
    		@PathVariable Long quizId,
    		@PathVariable Long questionId) {
  	
        try {
            Quiz updatedQuiz = quizService.addQuestionToQuiz(quizId, questionId);
            return ResponseEntity.ok(updatedQuiz); 
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    */
    
    
    /*
    @DeleteMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<Quiz> removeQuestionFromQuiz(
            @PathVariable Long quizId, 
            @PathVariable Long questionId) {
    	
        try {
            Quiz updatedQuiz = quizService.removeQuestionFromQuiz(quizId, questionId);
            return ResponseEntity.ok(updatedQuiz); 
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }    	
        
    }
    */
    
    
}
