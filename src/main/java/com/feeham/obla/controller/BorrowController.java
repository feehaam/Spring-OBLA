package com.feeham.obla.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowController {
    @PostMapping("/books/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }
}
