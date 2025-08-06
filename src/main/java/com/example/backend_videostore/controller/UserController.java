package com.example.backend_videostore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_videostore.model.User;
import com.example.backend_videostore.security.JwtUtil;
import com.example.backend_videostore.service.UserService;

import jakarta.validation.Valid;

/*
  UserController handles authentication-related operations:
  - User registration
  - User login

  It provides two endpoints under /api/users:
  - POST /register: to create a new user
  - POST /login: to verify credentials and return a JWT token

  CrossOrigin is enabled to allow requests from the frontend.
*/
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    /* Injects the UserService which handles user-related logic */
    @Autowired
    private UserService userService;

    /* Injects JwtUtil for generating JWT tokens during login */
    @Autowired
    private JwtUtil jwtUtil;

    /* 
       POST /api/users/register
       Registers a new user.
       - Expects a User object in the request body
       - Validates the input using @Valid (e.g., required fields)
       - Calls the service to save the user in the database
       - Returns 201 Created with the saved user object
    */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User created = userService.register(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /* 
       POST /api/users/login
       Authenticates the user and returns a JWT token.
       - Expects a JSON with "email" and "password"
       - Finds the user by email
       - Compares the raw password with the stored (hashed) password using BCrypt
       - If valid, generates and returns a JWT token
       - If invalid, returns 401 Unauthorized
    */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        User user = userService.findByEmail(email);
        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(email); // create token
        return ResponseEntity.ok(Map.of("token", token)); // give token back
    }

}
