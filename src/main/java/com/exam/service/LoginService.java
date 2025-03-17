package com.exam.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import com.exam.dto.LoginDto;
import com.exam.model.User;
import com.exam.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	
	
	public User authenticate(
	    		LoginDto loginDto
	    		, HttpServletRequest request) {
	
    	User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail())    			
                .orElseThrow(() -> new BadCredentialsException("User not found with username or email: " + loginDto.getUsernameOrEmail())
        		);
	
		if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
		    throw new BadCredentialsException("Invalid password");
		    }
	    
	    return user;
	}
	
	
	
	public void createSecurityContext(User user, HttpServletRequest request) {
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
            );
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        

        // Stockage de l'authentification dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Persistance dans la session
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        
    }

}
