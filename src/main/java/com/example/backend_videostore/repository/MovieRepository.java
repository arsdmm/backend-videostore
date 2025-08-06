package com.example.backend_videostore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.backend_videostore.model.Movie;

/*
  MovieRepository is a Spring Data MongoDB repository interface
  used to perform CRUD and custom query operations on the "movies" collection.

  It extends MongoRepository<Movie, String>, which provides:
  - Basic CRUD methods (save, findAll, findById, deleteById, etc.)
  - Automatic implementation of custom query methods based on method names
*/
public interface MovieRepository extends MongoRepository<Movie, String> {

    /*
      Custom method to find movies where the title contains a specific string (case-insensitive).
      Used for search functionality.
    */
    List<Movie> findByTitleContainingIgnoreCase(String title);

    /*
      Custom method to find all movies/TV shows by type (e.g., "movie" or "tvshow").
      Used to filter listings by category.
    */
    List<Movie> findByType(String type);
}
