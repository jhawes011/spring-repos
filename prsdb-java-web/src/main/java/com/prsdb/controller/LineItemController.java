package com.prsdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prsdb.db.LineItemRepo;
import com.prsdb.model.LineItem;

@CrossOrigin
@RestController
@RequestMapping("/api/line-items")
public class LineItemController {

	@Autowired
	private LineItemRepo lineItemRepo;
	
	
	@GetMapping("/")
	public List<LineItem> getAll() {
		return lineItemRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<LineItem> getById(@PathVariable int id) {
		Optional<LineItem> li = lineItemRepo.findById(id);
		if (li.isPresent()) {
			return li; 
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id); 
		}
	}
	@PostMapping("")
	public LineItem add(@RequestBody LineItem lineItem) {
		return lineItemRepo.save(lineItem);
	}
	@PutMapping("/{id}")
	public void putLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and body must match");
		}
		if (lineItemRepo.existsById(id)) {
			lineItemRepo.save(lineItem);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
		}
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		if (lineItemRepo.existsById(id)) {
			lineItemRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
		}
	}
}
