package com.exam.service;

import com.exam.model.User;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }
}
