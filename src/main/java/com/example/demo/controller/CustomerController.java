package com.example.demo.controller;

import com.example.demo.domain.Customer;
import com.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.create(customer), HttpStatus.CREATED);
    }   // register user

    @GetMapping("/user/{token}")
    public ResponseEntity<?> getUserInfo(@PathVariable String token) {
        System.out.println("Getting user info for: " + token);
        try{
            return new ResponseEntity<>(customerService.findByKey(token), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }    //  get user info
    //  delete user
    @DeleteMapping("/user/{token}")
    public ResponseEntity<?> deleteUser(@PathVariable String token) {
        System.out.println("Deleting user: " + token);
        try {
            customerService.deleteByKey(token);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {
        System.out.println("Login: " + customer);
        try{
            return new ResponseEntity<>(customerService.login(customer.getUsername().toUpperCase(), customer.getPassword()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/searches/{token}")
    public ResponseEntity<?> getSearches(@PathVariable String token) {
        try{
            return new ResponseEntity<>(customerService.getSearches(token), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("No User Found!",HttpStatus.NOT_FOUND);
        }
    }
}
