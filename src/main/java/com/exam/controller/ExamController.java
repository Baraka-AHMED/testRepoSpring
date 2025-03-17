package com.exam.controller;


import com.exam.model.Exam;
import com.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/all")
    public List<Exam> findAll(){
        return examService.getAllExams();
    }

    @GetMapping("/find")
    public Optional<Exam> findById(@RequestParam Long id) {
        return examService.getExamById(id);
    }

    @PostMapping("/add")
    public void add(@RequestBody Exam exam){
        examService.addExam(exam);
    }

    @PutMapping("/update")
    public void update(@RequestBody Exam exam) {
        examService.updateExam(exam);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam Long id){
        examService.deleteExamById(id);
    }
}