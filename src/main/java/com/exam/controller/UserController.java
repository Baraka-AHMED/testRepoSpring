package com.exam.controller;

import com.exam.model.User;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private UserService userService;
    
}
