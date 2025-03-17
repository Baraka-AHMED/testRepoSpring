package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.dto.UserRegistrationDto;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new UserRegistrationDto()); // pour binder les données du formulaire
	    model.addAttribute("roles", UserRole.values()); // envoyer la liste des rôles
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userDto") @Valid UserRegistrationDto userDto
			, BindingResult bindingResult
			, Model model
			) {
		try {
			userService.registerNewUser(userDto);
			System.out.println("Register successful");
			return "redirect:/login";
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/register";
		}
	}
	

}
