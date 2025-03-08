package com.exam.repository;
import com.exam.model.Exam;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam,Long>{

}
