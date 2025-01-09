package com.example.restapi.JWT;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Arrays;



@Component
public class JwtFilter extends OncePerRequestFilter {
	
	  @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private UserDetailsService userDetailsService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {

	        String token = extractToken(request);

	        if (token != null && jwtUtil.isTokenValid(token, "user")) {  // Pass the username here
	            String username = jwtUtil.extractUsername(token);
	            String roles = jwtUtil.extractRoles(token);  // Extract roles from token

	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	            // Set the authentication with roles
	            UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(username, null,
	                            Arrays.stream(roles.split(","))
	                                    .map(role -> new SimpleGrantedAuthority(role))
	                                    .collect(Collectors.toList()));

	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
	    }

	    private String extractToken(HttpServletRequest request) {
	        String token = request.getHeader("Authorization");
	        if (token != null && token.startsWith("Bearer ")) {
	            return token.substring(7);
	        }
	        return null;
	    }
}
