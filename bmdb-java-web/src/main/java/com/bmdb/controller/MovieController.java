package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.MovieRepo;
import com.bmdb.model.Movie;

@CrossOrigin // Allow requests from React app
@RequestMapping("/api/movies") // Base URL for all movie-related endpoints
@RestController

public class MovieController {
	@Autowired // Automatically inject the MovieRepo bean
	private MovieRepo movieRepo;

	@GetMapping("/") // Endpoint to get all movies
	public List<Movie> getAll() {
		return movieRepo.findAll(); // Fetch all movies from the database
	}

	@GetMapping("/{id}") // Endpoint to get a movie by ID
	public Optional<Movie> getById(@PathVariable int id) {
		Optional<Movie> m = movieRepo.findById(id); // Fetch a movie by its ID
		// return movieRepo.findById(id).orElse(null); // Fetch a movie by its ID
		if (m.isPresent()) {
			return m; // Return the movie if found
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found for id " + id); // Return null if
																										// not found
		}
	}

	@PostMapping("") // Endpoint to create a new movie
	public Movie add(@RequestBody Movie movie) {
		return movieRepo.save(movie); // Save the new movie to the database
	}

	@PutMapping("/{id}") // Endpoint to update an existing movie
	public void putMovie(@PathVariable int id, @RequestBody Movie movie) {
		if (id != movie.getId()) { // Check if the movie exists
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and body must match"); 
		}
		if (movieRepo.existsById(id)) { // Check if the movie exists
			movieRepo.save(movie); // Save the updated movie to the database
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found for id " + id);
		}
	}
	
	
	@DeleteMapping("/{id}") // Endpoint to delete a movie by ID
	public void delete(@PathVariable int id) {
		if (movieRepo.existsById(id)) { // Check if the movie exists
			movieRepo.deleteById(id); // Delete the movie from the database
		} else {
		
		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found for id " + id); 
        }
	}
}
