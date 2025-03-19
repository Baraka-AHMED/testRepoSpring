package com.exam.repository;
import com.exam.model.Course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course,Long> {

	@Query("SELECT DISTINCT c FROM Course c JOIN Exam e ON e.course.id = c.id WHERE e.teacher.userId = :teacherId ORDER BY c.id ASC")
    List<Course> findCoursesByTeacherId(@Param("teacherId") Long teacherId);
	

}
