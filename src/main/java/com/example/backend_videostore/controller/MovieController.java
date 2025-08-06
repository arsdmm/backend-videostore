package com.example.backend_videostore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_videostore.model.Movie;
import com.example.backend_videostore.service.MovieService;

/*
  MovieController handles all incoming HTTP requests related to movies and TV shows.

  Base route: /api/movies

  Supports:
  - Adding a new movie
  - Getting all movies or filtering by type (movie / tvshow)
  - Getting a single movie by its ID
  - Searching for movies by title
  - Updating a movie by ID
  - Deleting a movie by ID

  Uses MovieService for business logic and interacts with Movie model.
*/
@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    /* Injects the MovieService to delegate logic for movie operations */
    @Autowired
    private MovieService movieService;

    /* 
       POST /api/movies/add
       Adds a new movie or TV show to the database.
       Expects a Movie object in the request body.
    */
    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.addMovie(movie));
    }

    /* 
       GET /api/movies
       Returns a list of all movies or, if "type" query param is provided,
       filters by type ("movie" or "tvshow").
    */
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String type) {
        if (type != null) {
            return ResponseEntity.ok(movieService.findByType(type));
        } else {
            return ResponseEntity.ok(movieService.getAllMovies());
        }
    }

    /* 
       GET /api/movies/{id}
       Returns a single movie or TV show by its unique ID.
       If not found, returns 404.
    */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* 
       GET /api/movies/search?title=xyz
       Searches for movies/TV shows whose title contains the given query string.
    */
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchByTitle(title));
    }

    /* 
       PUT /api/movies/update/{id}
       Updates an existing movie with new data provided in the request body.
    */
    @PutMapping("/update/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String id, @RequestBody Movie updatedMovie) {
        return ResponseEntity.ok(movieService.updateMovie(id, updatedMovie));
    }

    /* 
       DELETE /api/movies/delete/{id}
       Deletes a movie by its ID.
       Returns 204 No Content on success.
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
