package com.example.api.web.rest;

import java.util.List;
import java.util.Optional;

import com.example.api.domain.Address;
import com.example.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	public static final String CUSTOMER_NOT_FOUND = "Customer not found";

	private CustomerService customerService;
	private AddressService addressService;

	@Autowired
	public CustomerController(CustomerService customerService, AddressService addressService) {
		this.customerService = customerService;
		this.addressService = addressService;
	}

	@GetMapping
	public List<Customer> findAll() {
		List<Customer> lstCustomer = customerService.findAll();
		return lstCustomer;
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		Customer tempCustomer = customerService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CUSTOMER_NOT_FOUND));

		return tempCustomer;
	}

	@PutMapping
	public Customer edit(@Valid @RequestBody Customer theCustomer){
		customerService.save(theCustomer);
		return theCustomer;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Customer store(@Valid @RequestBody Customer theCustomer){
		Optional<Customer> tempCustomer = customerService.findById(theCustomer.getId());

		if(tempCustomer.isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer already exists");
		}

		return customerService.save(theCustomer);
	}

	@GetMapping("/address")
	public List<Address> findAllAddress() {
		return addressService.findAll();
	}

	@PostMapping("/address")
	@ResponseStatus(HttpStatus.CREATED)
	public Address storeAddress(@Valid @RequestBody Address theAddress){
		Optional<Customer> tempCustomer = customerService.findById(theAddress.getCustomer().getId());

		if(!tempCustomer.isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CUSTOMER_NOT_FOUND);
		}

		theAddress = addressService.loadAPIData(theAddress);

		return addressService.save(theAddress);
	}

	@DeleteMapping("/{customerId}")
	public String destroy(@PathVariable Long customerId){
		Customer tempCustomer = customerService.findById(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CUSTOMER_NOT_FOUND));

		customerService.deleteCustomer(customerId);

		return "Deleted customer id - " + customerId;
	}

}
