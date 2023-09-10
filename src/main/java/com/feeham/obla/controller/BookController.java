package com.feeham.obla.controller;

import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {
    @PostMapping("/books/create")
    public ResponseEntity<?> createBook(@RequestBody BookCreateDTO bookCreateDTO){
        return ResponseEntity.ok(bookCreateDTO);
    }

    @PutMapping("/books/update/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookUpdateDTO bookUpdateDTO){
        return ResponseEntity.ok(bookUpdateDTO);
    }

    @DeleteMapping("/books/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }

    @GetMapping("/books/all")
    public ResponseEntity<?> getAllBooks(){
        return ResponseEntity.ok("All Books");
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }
}
