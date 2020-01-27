package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public Customer save(Customer theCustomer) {
		return repository.save(theCustomer);
	}

	public void deleteCustomer(Long customerId) {
		repository.deleteById(customerId);
	}

	public Page<Customer> findAllPaginated(int page, int size){
		PageRequest pageRequest = PageRequest.of(
				page,
				size,
				Sort.Direction.ASC,
				"name");
		return new PageImpl<Customer>(
				repository.findAll(),
				pageRequest, size);
	}
}
