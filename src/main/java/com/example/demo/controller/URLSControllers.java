package com.example.demo.controller;

import com.example.demo.domain.URLSDataTransfer;
import com.example.demo.domain.WebsiteDTO;
import com.example.demo.service.URLSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:5173")
public class URLSControllers {

    @Autowired
    private URLSService urlsService;

    @PostMapping("/analyze")
    public ResponseEntity<URLSDataTransfer> getURLs(@RequestBody WebsiteDTO website) throws IOException {
        // Try to send back a Response Entity that we found it
        try{
        return new ResponseEntity<>(urlsService.grabInfo(website.getWebsite()), HttpStatus.OK);
        }
//        If we get an exception while running it sends back an error for the front end.
//        Can be invalid url or something.
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
