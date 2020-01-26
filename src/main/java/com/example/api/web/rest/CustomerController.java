package com.example.api.web.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public List<Customer> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@PutMapping
	public Customer edit(@RequestBody Customer theCustomer){
		service.save(theCustomer);

		return theCustomer;
	}

	@PostMapping
	public Customer store(@RequestBody Customer theCustomer){
		Optional<Customer> tempCustomer = service.findById(theCustomer.getId());

		if(tempCustomer.isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer already exists");
		}

		return service.save(theCustomer);
	}

	@DeleteMapping("/{customerId}")
	public String destroy(@PathVariable Long customerId){
		Customer tempCustomer = service.findById(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		service.deleteCustomer(customerId);

		return "Deleted customer id - " + customerId;
	}

}
