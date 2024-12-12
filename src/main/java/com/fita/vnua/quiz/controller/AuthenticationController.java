// Authentication Controller
package com.fita.vnua.quiz.controller;


import com.fita.vnua.quiz.model.dto.UserDto;
import com.fita.vnua.quiz.model.dto.request.AuthenticationRequest;
import com.fita.vnua.quiz.model.dto.response.AuthenticationResponse;
import com.fita.vnua.quiz.model.entity.User;
import com.fita.vnua.quiz.repository.UserRepository;
import com.fita.vnua.quiz.security.CustomUserDetailsService;
import com.fita.vnua.quiz.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        // Load user details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Generate tokens
        final String accessToken = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        // Return tokens
        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    // New endpoint for refreshing the access token
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody String refreshToken) {
        // Validate the refresh token and generate a new access token
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        if (username != null && !jwtTokenUtil.isTokenExpired(refreshToken)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, refreshToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto registrationRequest) {
        // Check if username already exists
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        // Check if email already exists (if email is used)
        if (registrationRequest.getEmail() != null &&
                userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists");
        }

        // Create new user
        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setFullName(registrationRequest.getFullName());

        // Set default role if not provided
        newUser.setRole(registrationRequest.getRole() != null
                ? registrationRequest.getRole()
                : User.Role.USER);

        // Save user
        userRepository.save(newUser);

        // Generate token for the new user
        final UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        // Return token
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(token, refreshToken));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String refreshToken) {
        // Logic to invalidate the refresh token
        // This could involve removing it from a database or in-memory store
        // For simplicity, we will just return a success message
        return ResponseEntity.ok("Logged out successfully");
    }
}