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

    /**
     * Borrow a book by specifying the book ID and due date.
     *
     * @param bookId   The ID of the book to borrow.
     * @param dueDate  The due date for returning the book.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/books/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @RequestBody LocalDate dueDate) {
        // Get the user ID from the authentication token
        String userName = getUserNameFromToken();
        borrowService.create(bookId, credentials.getUserId(), dueDate);
        return ResponseEntity.ok("Borrowed the book successfully.");
    }

    /**
     * Get the username from the authentication token.
     *
     * @return The username extracted from the authentication token.
     */
    public static String getUserNameFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Return a borrowed book by specifying the book ID.
     *
     * @param bookId The ID of the book to return.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
        borrowService.update(bookId, credentials.getUserId());
        return ResponseEntity.ok("Returned the book successfully.");
    }
}
