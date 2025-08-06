package com.example.backend_videostore.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/*
  JwtUtil is a utility class responsible for creating, parsing, and validating JWT tokens.

  It uses the JJWT library to:
  - Generate secure JWTs with expiration time
  - Extract the email (subject) from a token
  - Validate a token by comparing email and checking expiration

  The same secret key is used for both signing and verifying tokens.
*/
@Component
public class JwtUtil {

    /* 
       Secret key used for signing the token.
       HS512 is a strong HMAC algorithm.
    */
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); 

    /* 
       Token expiration time in milliseconds (24 hours).
    */
    private final long EXPIRATION_TIME = 86400000;

    /* 
       Generates a JWT token using the user's email as the subject.
       The token includes:
       - Subject (email)
       - Issued time (now)
       - Expiration time (now + 24h)
       - Signature using the secret key
    */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /* 
       Extracts the email (subject) from a given JWT token.
       Used for authentication logic in JwtAuthFilter.
    */
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /* 
       Validates the token by:
       - Extracting email and comparing it to the expected one
       - Ensuring the token has not expired
    */
    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    /* 
       Checks whether the token is expired by comparing its expiration date to the current time.
    */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
