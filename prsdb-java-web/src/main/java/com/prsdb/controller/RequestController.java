package com.prsdb.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prsdb.db.RequestRepo;
import com.prsdb.model.Request;
import com.prsdb.model.RequestRejectDTO;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController
{

	@Autowired
	private RequestRepo requestRepo;

	@GetMapping("/")
	public List<Request> getAll()
	{
		return requestRepo.findAll();
	}

	@GetMapping("/list-review/{userId}")
	public List<Request> getAllReview(@PathVariable int userId)
	{
		return requestRepo.findByStatusAndUserIdNot("REVIEW", userId);
	}

	@GetMapping("/{id}")
	public Optional<Request> getById(@PathVariable int id)
	{
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent())
		{
			return r;
		} else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@PostMapping("")
	public Request add(@RequestBody Request request)
	{
		request.setRequestNumber(generateRequestNumber());
		request.setStatus("NEW");
		request.setSubmittedDate(LocalDateTime.now());
		request.setTotal(0);
		return requestRepo.save(request);
	}

	@PutMapping("/submit-review/{id}")
	public void submitReview(@PathVariable int id)
	{
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent())
		{
			Request request = r.get();
			request.setStatus(request.getTotal() <= 50 ? "APPROVED" : "REVIEW");
			requestRepo.save(request);
		} else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@PutMapping("/{id}")
	public void putRequest(@PathVariable int id, @RequestBody Request request)
	{
		if (id != request.getId())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and body must match");
		}
		if (requestRepo.existsById(id))
		{
			requestRepo.save(request);
		} else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}
	@PutMapping	("/approve/{id}")
	public void approveRequest(@PathVariable int id)
	{
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent())
		{
			Request request = r.get();
			request.setStatus("APPROVED");
			requestRepo.save(request);
		} else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}
	@PutMapping	("/reject/{id}")
	public void rejectRequest(@PathVariable int id, @RequestBody RequestRejectDTO requestRejectDTO)
	{
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent())
		{
			Request request = r.get();
			request.setStatus("REJECTED");
			request.setReasonForRejection(requestRejectDTO.getReasonForRejection());
			requestRepo.save(request);
		} else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id)
	{
		if (requestRepo.existsById(id))
		{
			requestRepo.deleteById(id);
		} else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	private String generateRequestNumber()
	{
		String dateSection = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String prefix = "R";
		int nextRequestNum = 1;
		Optional<Request> newestRequest = requestRepo.findTopByOrderByRequestNumberDesc();
		if (newestRequest.isPresent())
		{
			String newestRequestNumStr = newestRequest.get().getRequestNumber().substring(7, 11);
			try
			{
				int newestRequestNum = Integer.parseInt(newestRequestNumStr);
				nextRequestNum = newestRequestNum + 1;
			} catch (NumberFormatException e)
			{
			}

		}
		String nextRequestNumStr = String.valueOf(nextRequestNum);
		while (nextRequestNumStr.length() < 4)
		{
			nextRequestNumStr = "0" + nextRequestNumStr;
		}
		return prefix + dateSection + nextRequestNumStr;
	}
}
