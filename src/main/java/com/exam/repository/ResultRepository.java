package com.exam.repository;

import com.exam.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
	
    List<Result> findByExamId(Long examId);
    
    @Query("SELECT r FROM Result r WHERE r.exam.id = :examID")
    List<Result> findResultByExam(Long examId);

    @Query("SELECT r FROM Result r WHERE r.exam.id = :examId AND r.student.userId = :userId")
    Result findResultByExamAndStudent(Long examId, Long userId);
}
