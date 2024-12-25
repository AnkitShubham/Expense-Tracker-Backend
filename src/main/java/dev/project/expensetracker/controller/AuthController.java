package dev.project.expensetracker.controller;

import dev.project.expensetracker.entities.User;
import dev.project.expensetracker.security.JwtUtil;
import dev.project.expensetracker.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody User tempUser) {
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(tempUser.getEmail(), tempUser.getPassword())
            );

            // Load UserDetails to generate JWT
            UserDetails userDetails = userDetailsService.loadUserByUsername(tempUser.getEmail());

            // Generate JWT token using UserDetails
            String jwtToken = jwtUtil.generateToken(userDetails);

            // Return the token as the response
            return jwtToken;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
