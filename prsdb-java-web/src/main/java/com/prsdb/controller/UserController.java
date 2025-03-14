package com.prsdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prsdb.model.User;
import com.prsdb.model.UserLogin;
import com.prsdb.db.UserRepo;


@CrossOrigin
@RestController
@RequestMapping("/api/users")

public class UserController {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserLogin userLogin;
	 @PostMapping("/login")
	    public Optional<User> login(@RequestBody User user) {
	        String userName = user.getUserName();
	        String password = user.getPassword();
	        Optional<User> u = userLogin.findByUserNameAndPassword(userName, password);
	        if (u.isPresent()) {
	            return u; 
	        } else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for username " + userName);
	        }
	    }
	//GET
	@GetMapping("/")
	public List<User> getAll() {
		return userRepo.findAll();
	}
	//GET
	@GetMapping("/{id}")
	public Optional<User> getById(@PathVariable int id) {
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u; 
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id " + id); 
		}
	}

	// POST
	@PostMapping("")
	public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}

	// PUT
	@PutMapping("/{id}")
	public void updateUser(@PathVariable int id, @RequestBody User user) {
		if (id != user.getId())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and body must match");
		}
		if (userRepo.existsById(id))
		{
			userRepo.save(user);
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found for id " + id);
		}
	}

	// DELETE
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found for id " + id);
		}
	}
}
