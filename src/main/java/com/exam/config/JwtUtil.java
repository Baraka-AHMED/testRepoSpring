package com.exam.config;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.web.embedded.JettyVirtualThreadsWebServerFactoryCustomizer;


@Component
public class JwtUtil {
	
	private final Key key;
	private final long expirationTime;
	private final long refreshExpirationTime;
	
	public JwtUtil(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.expiration}") long expirationTime,
		@Value("${jwt.refreshExpiration}") long refreshExpirationTime
	) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
		this.expirationTime = expirationTime;
		this.refreshExpirationTime = refreshExpirationTime;
	}
	
	public String generateToken(String username, String role) {
		Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); 
        
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	
	public String generateRefreshToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
            
            return !isTokenExpired(claims);
            
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported token: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("Malformed token: " + e.getMessage());
            return false;
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration != null && expiration.before(new Date());
    }

}


