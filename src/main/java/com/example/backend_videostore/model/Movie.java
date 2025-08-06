package com.example.backend_videostore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
  This class represents the Movie model (also used for TV shows).
  It is a MongoDB document stored in the "movies" collection.

  Lombok annotations:
  - @Data: generates getters, setters, toString, equals, and hashCode
  - @NoArgsConstructor: generates a no-arg constructor
  - @AllArgsConstructor: generates a constructor with all fields
*/
@Document(collection = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    /* 
       MongoDB document ID.
       Automatically generated and used to uniquely identify each movie.
    */
    @Id
    private String id;

    /* 
       Movie title (required).
       @NotBlank ensures the field is not null and not just whitespace.
    */
    @NotBlank(message = "Title is required")
    private String title;

    /* 
       Short description or synopsis of the movie (required).
    */
    @NotBlank(message = "Short description is required")
    private String shortDescription;

    /* 
       Price to rent the movie (required).
       @NotNull ensures a value must be provided.
    */
    @NotNull(message = "Rental price is required")
    private Double rentalPrice;

    /* 
       Price to purchase the movie (required).
    */
    @NotNull(message = "Purchase price is required")
    private Double purchasePrice;

    /* 
       URL of the movie poster image (required).
    */
    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    /* 
       Type of the content: e.g., "movie" or "tvshow" (required).
       Used to categorize and filter content.
    */
    @NotBlank(message = "Type is required")
    private String type;
}
