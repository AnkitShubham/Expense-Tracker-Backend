package dev.project.expensetracker.controller;

import dev.project.expensetracker.entities.User;
import dev.project.expensetracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            // Check if the email already exists
            Optional<User> existingUser = userRepo.getUserByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(409).body("Email is already in use.");
            }

            // Encrypt the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user with the encrypted password
            User savedUser = userRepo.addUser(user);

            // Return success response with created status
            return ResponseEntity.status(201).body(savedUser);
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(500).body("An error occurred while signing up.");
        }
    }

    @GetMapping("/getUserDetails")
    public ResponseEntity<?> getUserDetails(@RequestParam String email) {
        Optional<User> user = userRepo.getUserByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

//    @PostMapping("/api/v1/login")
//    public Optional<User> login(@RequestBody User tempUser) {
//        User user = userRepo.getUserByEmail(tempUser.getEmail());
//        if(user != null && user.getPassword().equals(tempUser.getPassword())) {
//            return user;
//        }
//        else {
//            return null;
//        }
//    }
}
