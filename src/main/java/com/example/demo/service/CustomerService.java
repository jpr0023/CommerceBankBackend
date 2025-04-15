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
    public String create(Customer customer) {
        customer.setUsername(customer.getUsername().toUpperCase());

        int randomKey = (int) (Math.random() * 1000000);

        while (customerRepository.existsByToken(customer.getToken())) {
            randomKey = (int) (Math.random() * 1000000);
        }

        customer.setToken(String.valueOf(randomKey));
        return customerRepository.save(customer).getToken();
    }

    @Transactional   // delete user
    public void deleteByKey(String key) {
        customerRepository.delete(customerRepository.findByToken(key));
    }

    public String login(String username, String password) {
        Customer customer = customerRepository.findByUsernameAndPassword(username,password).orElse(null);

        if (customer == null) {
            throw new RuntimeException("Customer Not Found");
        }

        return customer.getToken();
    }

    public Customer findByKey(String key) {
        return customerRepository.findByToken(key);
    }
}
