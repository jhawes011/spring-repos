package com.bmdb.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.model.Actor;

public interface ActorRepo extends JpaRepository<Actor, Integer> {
	// This interface extends JpaRepository, which provides CRUD operations for the
	// Actor entity.
	// The first parameter is the entity type (Actor), and the second is the ID type
	// (Integer).
	// No additional methods are defined here, but you can add custom query methods
	// if needed.

}
