package com.prsdb.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prsdb.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUserNameAndPassword(String userName, String password);
	
}
