package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Service
public class AddressService {

    private AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address save(Address theAddress) {
        return repository.save(theAddress);
    }

    public List<Address> findAll() {
        return repository.findAll();
    }

    public Address loadAPIData(Address theAddress) {
        RestTemplate restTemplate = new RestTemplate();
        Address api = restTemplate.getForObject("https://viacep.com.br/ws/" + theAddress.getCep() + "/json/", Address.class);

        api.setId(theAddress.getId());
        api.setCustomer(theAddress.getCustomer());

        return api;
    }
}
