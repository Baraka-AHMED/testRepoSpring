package com.exam.repository;
import com.exam.model.Course;
import com.exam.model.User;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    // Récupérer les cours enseignés par un professeur
    @Query("SELECT DISTINCT c FROM Course c WHERE c.teacher.userId = :teacherId ORDER BY c.id ASC")
    List<Course> findCoursesByTeacherId(@Param("teacherId") Long teacherId);

    // Inscrire un étudiant à un cours
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO course_students (student_id, course_id) VALUES (:studentId, :courseId)", nativeQuery = true)
    void enrollStudentInCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // Désinscrire un étudiant d'un cours
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM course_students WHERE student_id = :studentId AND course_id = :courseId", nativeQuery = true)
    void unenrollStudentFromCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // Liste des étudiants inscrits à un cours
    @Query("SELECT s FROM User s JOIN s.courses c WHERE c.id = :courseId")
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);
}

