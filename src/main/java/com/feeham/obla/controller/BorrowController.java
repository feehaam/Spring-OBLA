package com.feeham.obla.controller;

import com.feeham.obla.service.interfaces.BorrowService;
import com.feeham.obla.service.interfaces.UserCredentialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class BorrowController {

    private BorrowService borrowService;
    private UserCredentialsService credentials;

    public BorrowController(BorrowService borrowService, UserCredentialsService userCredentialsService) {
        this.borrowService = borrowService;
        this.credentials = userCredentialsService;
    }

    @PostMapping("/books/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @RequestBody LocalDate dueDate){
        // user id
        String userName = getUserNameFromToken();
        borrowService.create(bookId, credentials.getUserId(), dueDate);
        return ResponseEntity.ok("Borrowed the book successfully.");
    }

    public static String getUserNameFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId){
        borrowService.update(bookId, credentials.getUserId());
        return ResponseEntity.ok("Returned the book successfully.");
    }
}
