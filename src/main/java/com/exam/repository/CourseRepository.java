package com.exam.repository;
import com.exam.model.Course;
import com.exam.model.Exam;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course,Long> {}
