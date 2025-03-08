package com.exam.service;

import com.exam.model.Exam;
import com.exam.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return (List<Exam>) examRepository.findAll();
    }

    public void addExam(Exam exam) {
        examRepository.save(exam);
    }

    public void deleteExamById(Long id) {
        examRepository.deleteById(id);
    }
}