package com.prsdb.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prsdb.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer> {
	 Optional<Request> findTopByOrderByRequestNumberDesc();
}
