package com.example.demo.controller;

import com.example.demo.domain.Customer;
import com.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173") // Assuming your frontend runs on this port
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        System.out.println("Registering user: " + customer.getName());
        return new ResponseEntity<>(customerService.create(customer), HttpStatus.CREATED);
    }   // register user

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable String userId) {
        System.out.println("Getting user info for: " + userId);
        Customer customer = customerService.findByUserId(userId);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }    //  get user info
    //  delete user
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        System.out.println("Deleting user: " + userId);
        boolean deleted = customerService.deleteByUserId(userId);
        if (deleted) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }   //
}
