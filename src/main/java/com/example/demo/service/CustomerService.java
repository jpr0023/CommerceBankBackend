package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer) {
        customer.setUsername(customer.getUsername().toUpperCase());
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)  // pass userid to find info
    public Customer findByUserName(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }

    @Transactional   // delete user
    public void deleteByUserName(String username) {
        customerRepository.delete(findByUserName(username));
    }
}
