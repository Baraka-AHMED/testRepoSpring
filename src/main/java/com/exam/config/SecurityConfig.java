package com.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Autoriser l'accès aux pages publiques (inscription, connexion)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/","/register", "/login", "/h2-console/**", "/users/**").permitAll()  // Permet l'accès aux routes d'inscription et de connexion
                    .anyRequest().authenticated()  // Toutes les autres pages nécessitent une authentification
            )
            /*
             * Cette configuration délègue totalement la gestion des requêtes Login à Spring
             * Si on l'utilise, il ne faut plus créer un controlleur qui gère la requête POST pour le login
             * Pour l'instant ça nemarche pas
             * 
            .formLogin(form -> 
                form
                    .loginPage("/login") // URL de la page de connexion
                    .loginProcessingUrl("/login") // pour gérer les requêtes POST sur /login
                    .defaultSuccessUrl("/home", true)
                    .permitAll() // Permet l'accès à la page de login sans authentification
            )
            
            */
            .logout(logout -> 
                logout
                    .permitAll() // Permet à tout le monde de se déconnecter
            )
            .csrf(csrf -> 
            		csrf.ignoringRequestMatchers("/h2-console/**", "/login")
            		)
         // Autorisation de l'affichage de la console H2 dans un iframe, s'applique aussi pour toute l'application
            .headers(headers ->
                headers.frameOptions(frameOptionsConfig 
                		-> frameOptionsConfig.sameOrigin()
                		)
            );
                    
        return http.build(); // Construction du filtre de sécurité avec la configuration définie
    }

	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }

	
	
}
