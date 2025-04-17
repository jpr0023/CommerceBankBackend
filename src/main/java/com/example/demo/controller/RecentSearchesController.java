package com.example.demo.controller;


import com.example.demo.service.RecentSearchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:5173")
public class RecentSearchesController {

    @Autowired
    private RecentSearchesService recentSearchesService;

    @GetMapping("/recent/searches/{token}")
    public ResponseEntity<?> getRecentSearches(@PathVariable String token) {
        try {
            return new ResponseEntity<>(recentSearchesService.getRecentSearches(token), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
