package com.bmdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.db.MovieRepo;
import com.bmdb.model.Movie;

@CrossOrigin // Allow requests from React app
@RequestMapping("/api/movies") // Base URL for all movie-related endpoints
@RestController

public class MovieController {
	@Autowired // Automatically inject the MovieRepo bean
	private MovieRepo movieRepo;
	
	@GetMapping("/") // Endpoint to get all movies
	public List<Movie> getAllMovies() {
		return movieRepo.findAll(); // Fetch all movies from the database
	}
	
	
	
	
}
