package com.example.restapi.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.restapi.JWT.JwtFilter;


import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;






@Configuration
public class SecurityConfig {
	
//	
//	 @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http
//	            .csrf().disable() // Disable CSRF for simplicity, enable in production
//	            .authorizeHttpRequests(auth -> auth
//	                .requestMatchers("/api/", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Public endpoints
//	                .anyRequest().authenticated() // Secure all other endpoints
//	            )
//	            .formLogin().and()
//	            .httpBasic();
//
//	        
//
//
//	        return http.build();
//	    }
//
//
//	 @Bean
//	    public InMemoryUserDetailsManager userDetailsManager() {
//	        UserDetails user = User.builder()
//	                .username("user") // Replace with your desired username
//	                .password(passwordEncoder().encode("password")) // Replace with your desired password
//	                .roles("USER")
//	                .build();
//	        return new InMemoryUserDetailsManager(user);
//	    }
//
//	 
//	    @Bean
//	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//	        return authConfig.getAuthenticationManager();
//	    }
//
//	    
//	    @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();
//	    }

	@Autowired
	@Lazy
    private JwtFilter jwtFilter;

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable()
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/auth/**").permitAll()
	                .requestMatchers("/api/", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
	                .requestMatchers("/api/students/**").hasRole("USER")
	                .anyRequest().authenticated()
	            )
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

	    @Bean
	    public InMemoryUserDetailsManager userDetailsManager() {
	        User.UserBuilder users = User.builder().passwordEncoder(passwordEncoder()::encode);
	        return new InMemoryUserDetailsManager(
	                users.username("user").password("password").roles("USER").build()
	        );
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
