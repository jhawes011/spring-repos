package com.prsdb.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prsdb.db.UserRepo;

@Service
public class UserLogin {
	
	@Autowired
	private UserRepo userRepo;

	public Optional<User> findByUserNameAndPassword(String userName, String password) {
		return userRepo.findByUserNameAndPassword(userName, password);
	}
}
