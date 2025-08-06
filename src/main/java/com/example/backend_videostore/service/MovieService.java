package com.example.backend_videostore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_videostore.model.Movie;
import com.example.backend_videostore.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(String id) {
        return movieRepository.findById(id);
    }

    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> findByType(String type) {
    return movieRepository.findByType(type);
    }


    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }

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
