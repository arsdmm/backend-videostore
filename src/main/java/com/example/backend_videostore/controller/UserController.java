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

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User created = userService.register(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Login endpoint with JWT generation
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        User user = userService.findByEmail(email);
        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(email); // ← создаём токен
        return ResponseEntity.ok(Map.of("token", token)); // ← возвращаем токен
    }

}
