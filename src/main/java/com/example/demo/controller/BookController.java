package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @CrossOrigin
    @GetMapping("/hello")
    public String save(){

        return "Hello World";

    }

    @CrossOrigin
    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book){
        return new ResponseEntity<>(bookService.create(book), HttpStatus.CREATED);

    }


    @GetMapping("/books")
    public ResponseEntity<?> findBooks(){
        return new ResponseEntity<>(bookService.AllBook(), HttpStatus.OK);

    }



}
