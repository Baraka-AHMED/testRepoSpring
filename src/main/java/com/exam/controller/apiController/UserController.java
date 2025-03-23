package com.exam.controller.apiController;

import com.exam.dto.UserRegistrationDto;
import com.exam.model.Course;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.CourseService;
import com.exam.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CourseService courseService;

    // 2. Modification d'un utilisateur (sans changement de mot de passe)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, 
                                             @RequestParam String firstName, 
                                             @RequestParam String lastName, 
                                             @RequestParam UserRole role, 
                                             @RequestParam boolean active) {
        try {
            userService.updateUserDetails(id, firstName, lastName, role, active);
            return ResponseEntity.ok("Utilisateur mis à jour avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour de l'utilisateur.");
        }
    }

    // 3. Liste de tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 4. Liste des utilisateurs par rôle (Admin, Teacher, Student)
    @GetMapping("/byRole")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam UserRole role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    // 5. Désactivation d'un utilisateur
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id)
        		.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        
        user.setActive(false);
        
        return ResponseEntity.ok("User desactivated succesfully");
    }

    // 6. Inscription d'un utilisateur (étudiant) à un cours
    @PostMapping("/enroll/{userId}/{courseId}")
    public ResponseEntity<String> enrollUserToCourse(@PathVariable("userId") Long userId, 
                                                     @PathVariable("courseId") Long courseId) {
    	
    	User user = userService.getUserById(userId)
    			.orElseThrow(()-> new RuntimeException("User not found"));
    	
    	Course course = courseService.getCourseById(courseId)
    			.orElseThrow(()-> new RuntimeException("Course not found"));
    	
        userService.enrollStudentToCourse(user, course);
        
        return ResponseEntity.ok("Utilisateur inscrit au cours avec succès.");
        
    }
}

