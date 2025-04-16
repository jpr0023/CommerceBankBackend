package com.example.demo.controller;

import com.example.demo.service.SavedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:5173")
public class SavedUrlController {
    @Autowired
    private SavedUrlService savedUrlService;






}
