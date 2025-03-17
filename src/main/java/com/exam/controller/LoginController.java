package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.exam.config.CustomUserDetailsService;
import com.exam.dto.LoginDto;
import com.exam.model.User;
import com.exam.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	UserService userService;
	
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
				
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Invalid login credentials");
		}
		
		try {
			
			userService.authenticate(loginDto);
			System.out.println("Login Succesful");
			
			// Mise en contexte sécurité de l'utilisateur authentifié
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsernameOrEmail());
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
			// ✅ ⚠️ Ajoute le contexte de sécurité à la session pour qu'il soit conservé après redirection
	        HttpSession session = request.getSession(true);
	        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			
			
			return "redirect:/";
			
		} catch (Exception e) {
			model.addAttribute("error", "Invalid username/email or password");
			System.out.println("Login failed!");
			return "login";
		}
		
	}
	
	
}
