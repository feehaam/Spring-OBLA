package com.feeham.obla.controller;

import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookUpdateDTO;
import com.feeham.obla.service.interfaces.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books/create")
    public ResponseEntity<?> createBook(@RequestBody BookCreateDTO bookCreateDTO){
        bookService.create(bookCreateDTO);
        return ResponseEntity.ok("New book created.");
    }

    @PutMapping("/books/update/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookUpdateDTO bookUpdateDTO){
        bookUpdateDTO.setBookId(bookId);
        bookService.update(bookUpdateDTO);
        return ResponseEntity.ok("Book updated successfully.");
    }

    @DeleteMapping("/books/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }

    @GetMapping("/books/all")
    public ResponseEntity<?> getAllBooks(){
        return ResponseEntity.ok(bookService.readAll());
    }

    @DeleteMapping("/books/delete")
    public ResponseEntity<?> getBookById(@RequestBody Long bookId){
        bookService.delete(bookId);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
