package com.bmdb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.model.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
	List<Movie> findByRating(String rating);
	// This interface extends JpaRepository, which provides CRUD operations for the
	// Movie entity.
	// The first parameter is the entity type (Movie), and the second is the ID type
	// (Integer).
	// No additional methods are defined here, but you can add custom query methods
	// if needed.

}
