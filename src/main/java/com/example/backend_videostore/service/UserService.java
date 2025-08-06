package com.example.backend_videostore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend_videostore.model.User;
import com.example.backend_videostore.repository.UserRepository;

/*
  UserService contains the business logic for handling user-related operations:
  - Registration
  - Login password verification
  - Finding users by email

  It interacts with the UserRepository to perform database operations.
*/
@Service
public class UserService {

    /* Injects the UserRepository to access user data in MongoDB */
    @Autowired
    private UserRepository userRepository;

    /* BCrypt encoder used to hash and verify passwords securely */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /* 
       Registers a new user:
       - Hashes the plain-text password using BCrypt
       - Saves the user to the database
       - Returns the saved User object
    */
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /* 
       Verifies user login credentials:
       - Looks up user by email
       - If found, compares the raw password with the stored hashed password
       - Returns true if match, otherwise false
    */
    public boolean login(String email, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent() && passwordEncoder.matches(rawPassword, optionalUser.get().getPassword());
    }

    /* 
       Finds and returns a User by their email.
       Returns null if no user is found.
       Used mainly for authentication.
    */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
