package com.exam.service;

import com.exam.dto.LoginDto;
import com.exam.dto.UserRegistrationDto;
import com.exam.model.User;
import com.exam.model.UserRole;
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
    	
    	if(userRepository.existsByEmail(dto.getEmail())) {
    		throw new RuntimeException("Email alreay used");
    	}
    	
    	if(userRepository.existsByUsername(dto.getUsername())) {
    		throw new RuntimeException("Username already used");
    	}
    	
    	try {
			User user = mapDtoToUser(dto);
			userRepository.save(user);
		} catch (Exception  e) {
			throw new RuntimeException(e.getMessage());
		}
    }
    
    
}