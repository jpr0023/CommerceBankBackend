package com.example.demo.controller;
import com.example.demo.domain.URLS;
import com.example.demo.domain.URLSDataTransfer;
import com.example.demo.domain.WebsiteDTO;
import com.example.demo.service.URLSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class URLSControllers {

    @Autowired
    private URLSService urlsService;

    @GetMapping("/analyze")
    public ResponseEntity<URLSDataTransfer> getURLs(@RequestBody WebsiteDTO website) throws IOException {
        return new ResponseEntity<>(urlsService.grabInfo(website.getWebsite()), HttpStatus.OK);
    }
    @CrossOrigin
    @PostMapping("/urls")
        public ResponseEntity<?> save(@RequestBody URLS url){
        return new ResponseEntity<>(urlsService.create(url), HttpStatus.CREATED);

    }



}
