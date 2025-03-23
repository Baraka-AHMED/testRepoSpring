package com.exam.service;

import com.exam.model.Exam;
import com.exam.model.ExamStatus;
import com.exam.model.Result;
import com.exam.repository.ResultRepository;
import com.exam.dto.ResultDto;
import com.exam.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    	
        Exam exam = examRepository.findById(examId).orElse(null);
        
        if (exam != null && exam.getExamStatus().name().equals("PUBLISHED")) {
            for (ResultDto resultDTO : resultDTOs) {
                Result result = new Result();
                result.setScore(resultDTO.getScore());
                resultRepository.save(result);
            }

            exam.setExamStatus(ExamStatus.CLOSED);
            examRepository.save(exam);
            return true;
        }
        return false;
    }
}
