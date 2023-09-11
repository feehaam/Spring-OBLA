package com.feeham.obla.controller;

import com.feeham.obla.service.interfaces.BorrowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowController {

    private BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/books/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId){
        borrowService.create(bookId, 1L);
        return ResponseEntity.ok("Borrowed the book successfully.");
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId){
        borrowService.update(bookId, 1L);
        return ResponseEntity.ok("Returned the book successfully.");
    }
}
