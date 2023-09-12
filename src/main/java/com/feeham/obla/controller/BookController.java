package com.feeham.obla.controller;

import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookUpdateDTO;
import com.feeham.obla.service.interfaces.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getAllBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();
            if (!roles.isEmpty()) {
                String role = roles.get(0);
                return ResponseEntity.ok(bookService.readAll(role));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }


    @DeleteMapping("/books/delete")
    public ResponseEntity<?> getBookById(@RequestBody Long bookId){
        bookService.delete(bookId);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
