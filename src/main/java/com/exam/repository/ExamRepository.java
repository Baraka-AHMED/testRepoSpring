package com.exam.repository;

import com.exam.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query("SELECT e FROM Exam e WHERE e.teacher.username = :teacherName ORDER BY e.id ASC LIMIT 1")
    Exam findFirstExamByTeacher(@Param("teacherName") String teacherName);
}
