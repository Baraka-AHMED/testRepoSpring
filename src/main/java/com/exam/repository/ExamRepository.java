package com.exam.repository;

import com.exam.model.Exam;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query("SELECT e FROM Exam e WHERE e.teacher.username = :teacherName ORDER BY e.id ASC LIMIT 1")
    Exam findFirstExamByTeacher(@Param("teacherName") String teacherName);

    @Query("SELECT e FROM Exam e WHERE e.teacher.userId = :teacherId ORDER BY e.id ASC")
    List<Exam> findExamsByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT e FROM Exam e WHERE e.course.id = :courseId ORDER BY e.id ASC")
    List<Exam> findExamsByCourseId(@Param("courseId") Long courseId);

	Optional<Exam> findExamById(Long examId);
}
