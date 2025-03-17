package com.exam.view;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ViewController {
	

	@RequestMapping("/")
    public String root(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication.getPrincipal() instanceof String
						&& authentication.getPrincipal().equals("anonymousUser"))) {
			
			System.out.println("User logged in!");
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			System.out.println(userDetails.getUsername());
			model.addAttribute("username", userDetails.getUsername());
			
			String role = userDetails.getAuthorities().stream()
		            .map(GrantedAuthority::getAuthority)
		            .findFirst()
		            .orElse("");
			
			switch (role) {
	            case "ADMIN":
	                return "redirect:/admin/home";
	            case "TEACHER":
	                return "redirect:/teacher/home";
	            case "STUDENT":
	                return "redirect:/student/home";
	            default:
	                return "redirect:/login";
			}
			
		}
		
		System.out.println("Login failed!");
		
		return "redirect:/login";
    }
	
	
	@RequestMapping("/admin/home")
	public String admin_root(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
        if (authentication != null && authentication.isAuthenticated()) {
        	
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());  
            
            return "admin_dashboard";
        }
        
        return "redirect:/login";
	}
	
	
	@RequestMapping("/teacher/home")
	public String teacher_root(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
        if (authentication != null && authentication.isAuthenticated()) {
        	
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());  
            
            return "teacher_dashboard";
        }
        
        return "redirect:/login";
	}
	
	
	@RequestMapping("/student/home")
	public String student(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
        if (authentication != null && authentication.isAuthenticated()) {
        	
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());  
            
            return "student_dashboard";
        }
        
        return "redirect:/login";
	}
	
    
}
