package com.exam.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController {
	

	@RequestMapping("/")
    public String root(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication.getPrincipal() instanceof String
						&& authentication.getPrincipal().equals("anonymousUser"))) {
			
			System.out.println("login ok 2");
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			System.out.println(userDetails.getUsername());
			model.addAttribute("username", userDetails.getUsername());
			
			return "home";
		}
		
		System.out.println("login ko");
		
		return "login";
    }
	
    
}
