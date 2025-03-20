package com.exam.controller.apiController;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.CourseService;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public List<User> findAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/find")
    public Optional<User> findById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add")
    public void add(@RequestBody User user){
        userService.addUser(user);
    }

    @PutMapping("/update")
    public void update(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam Long id){
        userService.deleteUserById(id);
    }

    @GetMapping("/role")
    public List<User> getUsersByRole(@RequestParam UserRole role) {
        return userService.getUsersByRole(role);
    }
    
    @PostMapping("/enroll")
    public void enrollInCourse(@RequestParam Long studentId, @RequestParam Long courseId) {

        try {
        	courseService.enrollStudentInCourse(studentId, courseId);
        }catch (Exception e) {
			System.out.println("Inscription échoué - "+e.getMessage());
		}       
        
    }

}
