package com.example.restapi.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	  @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @PostMapping("/login")
	    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
	        // Authenticate the user
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
	        );

	        // Cast the principal to Spring Security's User
	        User user = (User) authentication.getPrincipal();
//	        String token = jwtUtil.generateToken(user.getUsername());
	        String token = jwtUtil.generateToken(user.getUsername(), "ROLE_USER");

	        return ResponseEntity.ok(new AuthResponse(token));
	    }

	    // DTO for login requests
	    static class AuthRequest {
	        private String username;
	        private String password;

	        public String getUsername() {
	            return username;
	        }

	        public void setUsername(String username) {
	            this.username = username;
	        }

	        public String getPassword() {
	            return password;
	        }

	        public void setPassword(String password) {
	            this.password = password;
	        }
	    }

	    // DTO for login responses
	    static class AuthResponse {
	        private String token;

	        public AuthResponse(String token) {
	            this.token = token;
	        }

	        public String getToken() {
	            return token;
	        }
	    }
}
