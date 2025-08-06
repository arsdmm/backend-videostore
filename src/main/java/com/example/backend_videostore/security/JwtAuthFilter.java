package com.example.backend_videostore.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
  JwtAuthFilter is a custom security filter that intercepts every HTTP request once
  (extends OncePerRequestFilter) and checks for a valid JWT token in the Authorization header.

  If the token is valid, it sets the user’s authentication in the Spring Security context.
  This allows the request to pass through secured endpoints.
*/
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    /* Injects JwtUtil, which provides methods to extract and validate JWT tokens */
    @Autowired
    private JwtUtil jwtUtil;

    /*
      This method runs for every incoming request and applies the following logic:
      1. If the request is for /api/users/login or /register, skip the filter.
      2. If there is no Authorization header or it doesn't start with "Bearer ", skip the filter.
      3. Otherwise, extract the token, decode the email, and set authentication in the security context
         if the user is not already authenticated.
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/users/login") || path.startsWith("/api/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
