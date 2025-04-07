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
        // Generate a userId if not provided
        if (customer.getUserId() == null || customer.getUserId().isEmpty()) {
            customer.setUserId(generateUserId());
        }
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)  // pass userid to find info
    public Customer findByUserId(String userId) {
        return customerRepository.findByuserId(userId).orElse(null);
    }

    @Transactional   // delete user
    public boolean deleteByUserId(String userId) {
        Optional<Customer> customer = customerRepository.findByuserId(userId);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return true;
        }
        return false;
    }




private String generateUserId() {

    // Generate a random 6-digit code
    int code = 100000 + (int) (Math.random()*900000);// This ensures a 6-digit number
    String userId = String.valueOf(code);
    while (customerRepository.findByuserId(userId).isPresent()) {
        code = 100000 + (int) (Math.random()*900000);
        userId = String.valueOf(code);
    }

    return userId;
}
}
