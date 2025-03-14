package com.prsdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prsdb.db.LineItemRepo;
import com.prsdb.db.RequestRepo;
import com.prsdb.model.LineItem;
import com.prsdb.model.Request;

@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")
public class LineItemController {

	@Autowired
	private LineItemRepo lineItemRepo;

	@Autowired
	private RequestRepo requestRepo;

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

	@GetMapping("/lines-for-req/{reqId}")
	public List<LineItem> getLinesForRequest(@PathVariable int reqId) {
		return lineItemRepo.findByRequestId(reqId);
	}

	@PostMapping("")
	public LineItem add(@RequestBody LineItem lineItem) {
		
		  lineItemRepo.save(lineItem);
	        recalculateLineItemTotal(lineItem.getRequest().getId());
		
		return lineItem;

	}

	@PutMapping("/{id}")
	public void putLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and body must match");
		}
		if (lineItemRepo.existsById(id)) {
			lineItemRepo.save(lineItem);
			recalculateLineItemTotal(lineItem.getRequest().getId());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		Optional<LineItem> lineItemOpt = lineItemRepo.findById(id);
		if (lineItemOpt.isPresent()) {
			LineItem lineItem = lineItemOpt.get();
			int requestId = lineItem.getRequest().getId();
			lineItemRepo.deleteById(id);
			recalculateLineItemTotal(requestId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
		}
	}

	private void recalculateLineItemTotal(int requestId) {
		Optional<Request> requestOpt = requestRepo.findById(requestId);
		if (requestOpt.isPresent()) {
			Request request = requestOpt.get();
			List<LineItem> lineItems = lineItemRepo.findByRequestId(requestId);
			double total = lineItems.stream().mapToDouble(li -> li.getQuantity() * li.getPrice()).sum();
			request.setTotal(total);
			requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + requestId);
		}
	}
	
}
