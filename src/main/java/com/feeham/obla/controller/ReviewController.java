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

    /**
     * Get reviews for a specific book by specifying the book ID.
     *
     * @param bookId The ID of the book to get reviews for.
     * @return A ResponseEntity containing a list of reviews for the book.
     */
    @GetMapping("/books/{bookId}/reviews")
    public ResponseEntity<?> getBookReviews(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.findByBookId(bookId));
    }

    /**
     * Create a review for a specific book by specifying the book ID and providing review details.
     *
     * @param bookId          The ID of the book to review.
     * @param reviewCreateDTO The details of the review to create.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/books/{bookId}/reviews/create")
    public ResponseEntity<?> createBookReview(@PathVariable Long bookId, @RequestBody ReviewCreateDTO reviewCreateDTO) {
        reviewService.create(bookId, credentials.getUserId(), reviewCreateDTO);
        return ResponseEntity.ok("Review created successfully");
    }

    /**
     * Update a review for a specific book by specifying the book ID, review ID, and providing updated review details.
     *
     * @param bookId          The ID of the book being reviewed.
     * @param reviewId        The ID of the review to update.
     * @param reviewUpdateDTO The updated review details.
     * @return A ResponseEntity with a success message.
     */
    @PutMapping("/books/{bookId}/reviews/{reviewId}/update")
    public ResponseEntity<?> updateBookReview(@PathVariable Long bookId, @PathVariable Long reviewId,
                                              @RequestBody ReviewUpdateDTO reviewUpdateDTO) {
        reviewService.update(reviewId, bookId, credentials.getUserId(), reviewUpdateDTO);
        return ResponseEntity.ok("Review updated successfully");
    }

    /**
     * Delete a review for a specific book by specifying the book ID and review ID.
     *
     * @param bookId   The ID of the book being reviewed.
     * @param reviewId The ID of the review to delete.
     * @return A ResponseEntity with a success message.
     */
    @DeleteMapping("/books/{bookId}/reviews/{reviewId}/delete")
    public ResponseEntity<?> deleteBookReview(@PathVariable Long bookId, @PathVariable Long reviewId) {
        reviewService.delete(credentials.getUserId(), reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }

    /**
     * Get a specific review by specifying the review ID.
     *
     * @param reviewId The ID of the review to retrieve.
     * @return A ResponseEntity containing the details of the review.
     */
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.readById(reviewId));
    }

    /**
     * Get all reviews.
     *
     * @return A ResponseEntity containing a list of all reviews.
     */
    @GetMapping("/review/all")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.ok(reviewService.readAll());
    }
}
