package com.exam.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.UserRegistrationDto;
import com.exam.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApiRegisterController {
	
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationDto userDto) {
        try {
            userService.registerNewUser(userDto);
            return ResponseEntity.ok("Utilisateur créé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
