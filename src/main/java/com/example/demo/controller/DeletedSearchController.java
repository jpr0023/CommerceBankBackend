package com.example.demo.controller;

import com.example.demo.service.DeletedSearchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class DeletedSearchController {


    private DeletedSearchService deletedSearchService;


    @GetMapping("/deleted/recover/{delete_id}/{token}")
    public ResponseEntity<?> deletedSearch(@PathVariable String delete_id, @PathVariable String token) {


        try {
            deletedSearchService.recover(delete_id, token);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deleted/all")
    public ResponseEntity<?> deletedSearchAll() {
        try{
            return new ResponseEntity<>(deletedSearchService.getDeletedSearches(),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
