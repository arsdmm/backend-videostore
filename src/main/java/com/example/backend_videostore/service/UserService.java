package com.example.backend_videostore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend_videostore.model.User;
import com.example.backend_videostore.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean login(String email, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent() && passwordEncoder.matches(rawPassword, optionalUser.get().getPassword());
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
