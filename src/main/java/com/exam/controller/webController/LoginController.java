package com.exam.controller.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.exam.config.CustomUserDetailsService;
import com.exam.dto.LoginDto;
import com.exam.model.User;
import com.exam.service.LoginService;
import com.exam.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@Controller 
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	
	@PostMapping("/login")
	public String login(
			@ModelAttribute("loginDto") @Valid LoginDto loginDto
			, BindingResult bindingResult
			, Model model
			,HttpServletRequest request 
			) {
		
		/*
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");
                */
		
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Invalid login credentials");
		}
		
		try {
			
			// Authentification de l'utilisateur
			User user = loginService.authenticate(loginDto, request);
			
			// Enregistrement de l'utilisateur dans le context de sécurité
			loginService.createSecurityContext(user, request);
			
			System.out.println("Login Succesful");
			
			String redirectURL = switch (user.getRole().name()) {
	            case "ADMIN" -> "/admin/dashboard";
	            case "TEACHER" -> "/teacher/dashboard";
	            case "STUDENT" -> "/student/dashboard";
	            default -> "/";
			};
			
			//System.out.println("Rôle : "+role);
			
			return "redirect:"+redirectURL;
			
		} catch (Exception e) {
			model.addAttribute("error", "Invalid username/email or password");
			System.out.println("Login failed!");
			return "login";
		}
		
	}
	
	
}
