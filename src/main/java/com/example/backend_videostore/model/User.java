package com.example.backend_videostore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
  This class represents the User model stored in the "users" MongoDB collection.

  Lombok annotations:
  - @Data: auto-generates getters, setters, toString, equals, and hashCode
  - @NoArgsConstructor: generates a no-arg constructor
  - @AllArgsConstructor: generates a constructor with all fields
*/
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /* 
       MongoDB document ID.
       Automatically generated and uniquely identifies the user.
    */
    @Id
    private String id;

    /* 
       Full name of the user (required).
       @NotBlank ensures it's not null or empty.
    */
    @NotBlank(message = "Full name is required")
    private String fullName;

    /* 
       User's email address (required and validated).
       @Email checks the format.
       @NotBlank ensures it's not null or empty.
    */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /* 
       User's password (required and validated).
       Constraints:
       - At least 8 characters long
       - Must include:
         • One uppercase letter
         • One lowercase letter
         • One number
         • One special character
    */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "Password must contain uppercase, lowercase, number, and special character"
    )
    private String password;
}
