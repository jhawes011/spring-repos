package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.CreditRepo;
import com.bmdb.model.Credit;

@CrossOrigin
@RestController
@RequestMapping("/api/credits")
public class CreditController {
	@Autowired
	private CreditRepo creditRepo;
	
	
	@GetMapping("/")
	public List<Credit> getAll() {
		return creditRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Credit> getById(@PathVariable int id) {
		Optional<Credit> c = creditRepo.findById(id);
		if (c.isPresent()) {
			return c; 
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found for id " + id); 
		}
	}
	@PostMapping("")
	public Credit add(@RequestBody Credit credit) {
		return creditRepo.save(credit);
	}
	@PutMapping("/{id}")
	public void putCredit(@PathVariable int id, @RequestBody Credit credit) {
		if (id != credit.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and body must match");
		}
		if (creditRepo.existsById(id)) {
			creditRepo.save(credit);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found for id " + id);
		}
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		if (creditRepo.existsById(id)) {
			creditRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found for id " + id);
		}
	}
	
}
