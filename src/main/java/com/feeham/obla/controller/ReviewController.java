package com.feeham.obla.controller;

import com.feeham.obla.model.reviewdto.ReviewCreateDTO;
import com.feeham.obla.model.reviewdto.ReviewUpdateDTO;
import com.feeham.obla.service.interfaces.ReviewService;
import com.feeham.obla.service.interfaces.UserCredentialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final UserCredentialsService credentials;

    public ReviewController(ReviewService reviewService, UserCredentialsService credentials) {
        this.reviewService = reviewService;
        this.credentials = credentials;
    }

    @GetMapping("/books/{bookId}/reviews")
    public ResponseEntity<?> getBookReviews(@PathVariable Long bookId){
        return ResponseEntity.ok(reviewService.findByBookId(bookId));
    }

    @PostMapping("/books/{bookId}/reviews/create")
    public ResponseEntity<?> createBookReview(@PathVariable Long bookId, @RequestBody ReviewCreateDTO reviewCreateDTO){
        reviewService.create(bookId, credentials.getUserId(), reviewCreateDTO);
        return ResponseEntity.ok("Review created successfully");
    }

    @PutMapping("/books/{bookId}/reviews/{reviewId}/update")
    public ResponseEntity<?> updateBookReview(@PathVariable Long bookId, @PathVariable Long reviewId,
                                              @RequestBody ReviewUpdateDTO reviewUpdateDTO){
        reviewService.update(reviewId, bookId, credentials.getUserId(), reviewUpdateDTO);
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/books/{bookId}/reviews/{reviewId}/delete")
    public ResponseEntity<?> deleteBookReview(@PathVariable Long bookId, @PathVariable Long reviewId){
        reviewService.delete(credentials.getUserId(), reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable Long reviewId){
        return ResponseEntity.ok(reviewService.readById(reviewId));
    }

    @GetMapping("/review/all")
    public ResponseEntity<?> getAllReviews(){
        return ResponseEntity.ok(reviewService.readAll());
    }
}
