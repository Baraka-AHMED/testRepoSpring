package com.exam.config;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserDetails principal;
	private String role;

    public JwtAuthenticationToken(UserDetails principal, String role) {
        super(principal, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        this.principal = principal;
        this.role = role;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; // Pas de mot de passe stocké ici, car c'est un token JWT
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
    
    public String getRole() {
        return role;
    }
}

