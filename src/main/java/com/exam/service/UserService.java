package com.exam.service;

import com.exam.dto.LoginDto;
import com.exam.dto.UserRegistrationDto;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.lang.RuntimeException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
 // Mapping DTO -> Entity (comme vu plus haut)
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

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public void addUser(User user){
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }
    
    
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        return userRepository.save(user);
    }
    
    public User authenticate(LoginDto loginDto) {

    	User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail())    			
                .orElseThrow(() -> new BadCredentialsException("User not found with username or email: " + loginDto.getUsernameOrEmail())
                		);
    	
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        
        return user;
    }
    

}
