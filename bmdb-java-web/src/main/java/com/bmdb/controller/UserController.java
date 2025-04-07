package com.bmdb.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.UserRepo;
import com.bmdb.model.User;
import com.bmdb.model.UserDTO;
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController
{
@Autowired
	private UserRepo userRepo;

	@GetMapping("/")
	public List<User> getAll()
	{
		return userRepo.findAll();
	}
	
	@PostMapping("/login")
	public User login(@RequestBody UserDTO userDTO)
	{
		Optional<User> u = userRepo.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
		if (u.isPresent())
		{
			return u.get();
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
		}
	}
	

}
