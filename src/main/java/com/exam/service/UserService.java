package com.exam.service;

import com.exam.dto.LoginDto;
import com.exam.dto.UserRegistrationDto;
import com.exam.model.Course;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repository.CourseRepository;
import com.exam.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mapping DTO -> Entity 
    private User mapDtoToUser(UserRegistrationDto dto) {
        User user = new User(); 
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return user;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            userRepository.save(user);
        }
    }
    
    // Méthode à utiliser quand le mot de passe a été modifié
    public void updateUserWithPassword (User user) {
    	if (userRepository.existsById(user.getUserId())) {
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }


    public void registerNewUser(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Nom d'utilisateur déjà utilisé !");
        }

        if (dto.getRole() == UserRole.ADMIN) {
            throw new RuntimeException("Création d'un administrateur interdite via l'inscription !");
        }

        User user = mapDtoToUser(dto);
        userRepository.save(user);
    }


    public void updateUserDetails(Long id, String firstName, String lastName, UserRole role, boolean active) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRole(role);
            user.setActive(active);
            userRepository.save(user);
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public void enrollStudentToCourse(User student, Course course) {
    	
    	student.getCourses().add(course);
    	course.getStudents().add(student);
    	
    	userRepository.save(student);
    	courseRepository.save(course);
    	
    }



}