package com.example.demo.controller;

import com.example.demo.service.SavedSearchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/saved")
public class SavedSearchesController {
    @Autowired
    private SavedSearchesService savedSearchesService;


    @GetMapping("/limit/{token}/{number}")
    public ResponseEntity<?> getSavedSearches(@PathVariable String token, @PathVariable String number) {
        int limit = Integer.parseInt(number);
        try {
            return new ResponseEntity<>(savedSearchesService.getSavedSearches(token, limit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rescan/{token}/{id}")
    public ResponseEntity<?> getRescan(@PathVariable String token, @PathVariable String id) {
        int urlId = Integer.parseInt(id);

        try {
            return new ResponseEntity<>(savedSearchesService.rescan(token,urlId),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/rename/{id}")
    public ResponseEntity<?> rename(@PathVariable String id, @RequestBody String name) {
        long urlId = Integer.parseInt(id);
        try{
            savedSearchesService.rename(urlId,name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        long savedSearchId = Integer.parseInt(id);
        try{
            savedSearchesService.deleteSavedSearch(savedSearchId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
