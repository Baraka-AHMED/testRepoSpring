package com.exam.repository;

import com.exam.model.Course;
import com.exam.model.User;
import com.exam.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") UserRole role);

    Optional <User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	Optional<User> findByUsernameOrEmail(String username, String email);

	@Query("SELECT u FROM User u JOIN u.courses c WHERE c = :course")
	List<User> getUsersByCourse(Course course);
	
    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u NOT IN (SELECT u FROM User u JOIN u.courses c WHERE c = :course)")
    List<User> getUnenrolledStudents(Course course);
    
    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u IN (SELECT u FROM User u JOIN u.courses c WHERE c = :course)")
    List<User> getEnrolledStudents(Course course);

}
