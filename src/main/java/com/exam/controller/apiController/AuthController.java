package com.exam.controller.apiController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.exam.config.JwtUtil;
import com.exam.dto.LoginDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
			@RequestBody @Valid LoginDto loginDto
			, BindingResult bindingResult
			, Model model
			,HttpServletResponse response
			,HttpServletRequest request 
			){

    	// Vérifier les erreurs de validation
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Erreur de validation.");
        }
    	
        // Authentification avec Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
        		.findFirst()
        		.map(grantedAuthority -> grantedAuthority.getAuthority())
        		.orElse("USER"); // Par défaut, on donne un rôle générique si aucun rôle n'est trouvé
        
        System.out.println("Role : " + role);
        
        String token = jwtUtil.generateToken(userDetails.getUsername(), role);

        boolean isRestApiRequest = request.getRequestURI().startsWith("/api/");

        if (isRestApiRequest) {
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            Cookie jwtCookie = new Cookie("JWT-TOKEN", token);
            jwtCookie.setHttpOnly(true);  // Empêche l'accès via JavaScript (protection XSS)
            jwtCookie.setSecure(false);  // Mettre à false en local si non HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(3600);  // 1 heure

            response.addCookie(jwtCookie);
            return ResponseEntity.ok("Authentification réussie et token stocké dans le cookie !");
        }
        
    }
}

// Classe pour la réponse contenant le token JWT (API REST)
class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
