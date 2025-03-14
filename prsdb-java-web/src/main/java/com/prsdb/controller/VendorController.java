package com.prsdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.prsdb.db.VendorRepo;

import com.prsdb.model.Vendor;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")

public class VendorController {
	@Autowired
	private VendorRepo vendorRepo;

	// GET
	@GetMapping("/")
	public List<Vendor> getAll() {
		return vendorRepo.findAll();
	}

	// GET
	@GetMapping("/{id}")
	public Optional<Vendor> getById(@PathVariable int id) {
		Optional<Vendor> v = vendorRepo.findById(id);
		if (v.isPresent()) {
			return v; 
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id " + id); 
																									
		}
	}

	// POST
	@PostMapping("")
	public Vendor addVendor(@RequestBody Vendor vendor) {
		return vendorRepo.save(vendor);
	}

	// PUT
	@PutMapping("/{id}")
	public void updateVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		if (id != vendor.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and body must match");
		}
		if (vendorRepo.existsById(id)) {
			vendorRepo.save(vendor);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id " + id);
		}
	}

	// DELETE
	@DeleteMapping("/{id}")
	public void deleteVendor(@PathVariable int id) {
		if (vendorRepo.existsById(id)) {
			vendorRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id " + id);
		}
	}
}
