package com.exam.service;

import com.exam.model.Exam;
import com.exam.model.ExamStatus;
import com.exam.model.Result;
import com.exam.model.User;
import com.exam.repository.ResultRepository;
import com.exam.dto.ResultDto;
import com.exam.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ExamRepository examRepository;

    public List<Result> getResults(Long examId) {
        return resultRepository.findByExamId(examId);
    }

    @Transactional
    public boolean validateResults(Long examId, List<ResultDto> resultDTOs) {
    	
        Exam exam = examRepository.findById(examId)
        		.orElseThrow(()-> new RuntimeException("Exam not found"));
        
        System.out.println("01");
        
        if (exam.getExamStatus().name().equals("PUBLISHED")) {
        	System.out.println("01");
            for (ResultDto resultDTO : resultDTOs) {
            	System.out.println("01");
                Result result = new Result();
                result.setExam(exam);
                result.setScore(resultDTO.getScore());
                resultRepository.save(result);
                System.out.println("0X");
            }

            System.out.println("02");

            exam.setExamStatus(ExamStatus.CLOSED);
            examRepository.save(exam);
            return true;
        }
        return false;
    }

 // Service pour récupérer les résultats par examen
    public List<Result> getResultsByExam(Long examId) {
        return resultRepository.findResultByExam(examId);
    }

    // Service pour récupérer un résultat par examen et utilisateur
    public Result getResultByExamAndStudent(Long examId, Long userId) {
        return resultRepository.findResultByExamAndStudent(examId, userId);
    }

	public void save(Result result) {
		resultRepository.save(result);
	}

	
}
