package com.exam.controller;

import com.exam.model.User;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public List<User> findAll(){
        return userService.getAllUsers();
    }

    @PutMapping("add")
    public void add(
            @RequestBody User user){
        userService.addUser(user);
    }

    @DeleteMapping("deleteById")
    public void deleteById(
            @RequestParam Long id){
        userService.deleteUserById(id);
    }

    @DeleteMapping("delete")
    public void delete(
            @RequestBody User user){
        userService.deleteUser(user);
    }
}
