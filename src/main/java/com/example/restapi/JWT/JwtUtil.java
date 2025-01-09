package com.example.restapi.JWT;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class JwtUtil {

	 private static final String SECRET_KEY = "M2ZkZ2Nna2M5YmFjN2Z3Y2pLZjNKLk0yTkJUOTZGV2xOQ3Q=\r\n";
	    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	    public String generateToken(String username, String role) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("roles", role);  // Add roles to the claims
	        return createToken(claims, username);
	    }

	    private String createToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	                .compact();
	    }

	    public String extractUsername(String token) {
	        return extractClaims(token).getSubject();
	    }

	    public Date extractExpiration(String token) {
	        return extractClaims(token).getExpiration();
	    }

	    public Claims extractClaims(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }

	    public boolean isTokenValid(String token, String username) {
	        String extractedUsername = extractUsername(token);
	        return (username.equals(extractedUsername) && !isTokenExpired(token));
	    }

	    private boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }
	    public String extractRoles(String token) {
	        Claims claims = extractClaims(token);
	        return (String) claims.get("roles");
	    }

}
