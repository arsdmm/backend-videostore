package com.example.backend_videostore.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/* 
   This class defines a CORS configuration for the backend using Spring Boot.
   CORS (Cross-Origin Resource Sharing) allows the frontend (hosted on another domain)
   to communicate with the backend by setting proper HTTP headers.
*/
@Configuration
public class CorsConfig {

    /* 
       This method defines a bean that configures CORS settings for the application.

       It allows:
       - Requests from the frontend deployed at "https://front-videostore.vercel.app"
       - HTTP methods: GET, POST, PUT, DELETE, OPTIONS
       - All headers
       - Credential sharing (cookies, tokens)

       The configuration is registered for all endpoints using "/**".
    */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("https://front-videostore.vercel.app"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
