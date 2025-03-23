package com.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec.FrameOptionsSpec;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.function.ServerRequest.Headers;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
	
	@Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
        	.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authz) -> authz
                    .requestMatchers("/", "/h2-console/**", "/login", "/register", "/api/**", "/api/register").permitAll() // Autorise /api/login sans authentification
                    .requestMatchers("/admin/**").hasRole("ADMIN")   // Accès réservé aux administrateurs
                    .requestMatchers("/teacher/**").hasRole("TEACHER") // Accès réservé aux enseignants
                    .requestMatchers("/student/**").hasRole("STUDENT") // Accès réservé aux étudiants
                    .anyRequest().authenticated() // Autres requêtes API nécessitent une authentification
                )
            .headers(Headers -> Headers.frameOptions(frameOps -> frameOps.sameOrigin()))
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement((sessionManagement) ->
                sessionManagement.disable()
            );

        return http.build();
    }
	
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }

	@Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtil, userDetailsService);  // Crée le bean de JwtFilter
    }
	
}
