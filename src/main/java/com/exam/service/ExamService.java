package com.exam.service;

import com.exam.model.Exam;
import com.exam.model.ExamStatus;
import com.exam.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    // Ajouter un examen
    public Exam addExam(Exam exam) {
        return examRepository.save(exam);
    }

    // Récupérer un examen par ID
    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    // Supprimer un examen par ID
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

    // Récupérer tous les examens d'un cours
    public List<Exam> getExamsByCourseId(Long courseId) {
        return examRepository.findByCourseId(courseId);
    }
    
    public void updateExam(Long examId, String examTitle, LocalDate examDate) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setExamTitle(examTitle);
        exam.setExamDate(examDate);
        examRepository.save(exam);
    }
    
    public boolean publishExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setExamStatus(ExamStatus.PUBLISHED);
        examRepository.save(exam);
        return true;
    }
    
}
