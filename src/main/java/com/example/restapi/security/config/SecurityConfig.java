package com.example.restapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {
	
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable() // Disable CSRF for simplicity, enable in production
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Public endpoints
	                .anyRequest().authenticated() // Secure all other endpoints
	            )
	            .formLogin().and() // Enable form-based login
	            .httpBasic(); // Enable basic HTTP authentication

	        return http.build();
	    }

	    @Bean
	    public InMemoryUserDetailsManager userDetailsManager() {
	        UserDetails user = User.builder()
	                .username("user")
	                .password(passwordEncoder().encode("password"))
	                .roles("USER")
	                .build();
	        UserDetails admin = User.builder()
	                .username("admin")
	                .password(passwordEncoder().encode("admin"))
	                .roles("ADMIN")
	                .build();
	        return new InMemoryUserDetailsManager(user, admin);
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
