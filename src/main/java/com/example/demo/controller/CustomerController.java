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
        System.out.println("Registering user: " + customer.getUsername());
        return new ResponseEntity<>(customerService.create(customer), HttpStatus.CREATED);
    }   // register user

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        System.out.println("Getting user info for: " + username);
        try{
            return new ResponseEntity<>(customerService.findByUserName(username.toUpperCase()), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }    //  get user info
    //  delete user
    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        System.out.println("Deleting user: " + username);
        try {
            customerService.deleteByUserName(username.toUpperCase());
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }   //
}
