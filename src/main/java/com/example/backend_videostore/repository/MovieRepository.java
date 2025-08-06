package com.example.backend_videostore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.backend_videostore.model.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByType(String type);
}

