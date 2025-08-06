package com.example.backend_videostore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_videostore.model.Movie;
import com.example.backend_videostore.repository.MovieRepository;

/*
  MovieService contains business logic related to movies and TV shows.
  It acts as a middle layer between the MovieController and MovieRepository.
*/
@Service
public class MovieService {

    /* Injects the MovieRepository to perform database operations */
    @Autowired
    private MovieRepository movieRepository;

    /* 
       Adds a new movie or TV show to the database.
       Uses MongoRepository's save() method.
    */
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    /* 
       Retrieves all movies and TV shows from the database.
    */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /* 
       Retrieves a movie or TV show by its ID.
       Returns Optional<Movie> to safely handle "not found" cases.
    */
    public Optional<Movie> getMovieById(String id) {
        return movieRepository.findById(id);
    }

    /* 
       Searches for movies or TV shows where the title contains the provided string (case-insensitive).
    */
    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    /* 
       Filters movies or TV shows by their type (e.g., "movie" or "tvshow").
    */
    public List<Movie> findByType(String type) {
        return movieRepository.findByType(type);
    }

    /* 
       Deletes a movie or TV show by its ID.
    */
    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }

    /* 
       Updates an existing movie by its ID with the fields from the provided updatedMovie object.
       If the movie is not found, throws a RuntimeException.
    */
    public Movie updateMovie(String id, Movie updatedMovie) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setShortDescription(updatedMovie.getShortDescription());
                    movie.setRentalPrice(updatedMovie.getRentalPrice());
                    movie.setPurchasePrice(updatedMovie.getPurchasePrice());
                    movie.setImageUrl(updatedMovie.getImageUrl());
                    return movieRepository.save(movie);
                })
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }
}
