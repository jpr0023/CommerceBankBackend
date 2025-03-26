package com.example.demo.controller;


import com.example.demo.domain.URLS;
import com.example.demo.domain.Users;
import com.example.demo.service.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

// This is the link with our development environment
//@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {
    @Autowired
    private UsersService userService;

    @PostMapping("/createUser")
    public ResponseEntity<Users> save(@RequestBody Users user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }


}
