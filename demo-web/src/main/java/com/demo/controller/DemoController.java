package com.demo.controller;
import org.springframework.web.bind.annotation.*;

import com.demo.model.Movie;
@RestController
@RequestMapping("/api/demo") 
public class DemoController {
	
	@GetMapping("/hello")
	public String sayHello()
	{
		return "Hello, World!";
	}
	
	@GetMapping("/name")
	public String getName(@RequestParam String firstName, @RequestParam String lastName) {
		return "Hello, " + firstName + lastName + "!";
	}
	
	 @GetMapping("/total/{price}/{quantity}")
	    public String getTotal(@PathVariable double price, @PathVariable int quantity) {
	        double total = price * quantity;
	        return "Total price: $" + total;
	    }
}
