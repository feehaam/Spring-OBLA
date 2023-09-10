package com.feeham.obla.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @GetMapping("/books/{bookId}/reviews")
    public ResponseEntity<?> getBookReviews(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }

    @PostMapping("/books/{bookId}/reviews/create")
    public ResponseEntity<?> createBookReview(@PathVariable Long bookId){
        return ResponseEntity.ok(bookId);
    }

    @PutMapping("/books/{bookId}/reviews/{reviewId}/update")
    public ResponseEntity<?> updateBookReview(@PathVariable Long bookId, @PathVariable Long reviewId){
        return ResponseEntity.ok(reviewId);
    }

    @DeleteMapping("/books/{bookId}/reviews/{reviewId}/delete")
    public ResponseEntity<?> deleteBookReview(@PathVariable Long bookId, @PathVariable Long reviewId){
        return ResponseEntity.ok(reviewId);
    }
}
