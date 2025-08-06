package com.example.backend_videostore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backend_videostore.security.JwtAuthFilter;

/* 
   SecurityConfig defines the security rules for the backend using Spring Security.
   It sets up which endpoints are public, which require authentication,
   and integrates a custom JWT filter for token-based authentication.
*/
@Configuration
public class SecurityConfig {

    /* 
       Injects a custom JWT filter that will be used to validate tokens
       before the standard UsernamePasswordAuthenticationFilter.
    */
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    /* 
       Defines the security filter chain for HTTP requests.

       Configuration details:
       - CORS is enabled (configured elsewhere).
       - CSRF is disabled since the app uses token-based (stateless) auth.
       - Session management is set to STATELESS, so no HTTP session is created.
       - Authorization rules:
         • Public access (no auth required) for:
           - POST /api/users/register
           - POST /api/users/login
           - GET /api/movies
           - GET /api/movies/search
           - GET /api/movies/{id}
         • Authenticated access required for:
           - POST /api/movies/add
           - PUT /api/movies/update/**
           - DELETE /api/movies/delete/**
       - Adds the custom JWT filter before the standard authentication filter.
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/movies").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/movies/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/movies/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/movies/add").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/movies/update/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/movies/delete/**").authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
