package com.feeham.obla.utilities.mapping;

import com.feeham.obla.entity.Review;
import com.feeham.obla.model.reviewdto.ReviewReadDTO;

public class ReviewConverter {

    // This utility method converts a Review entity to a ReviewReadDTO data transfer object.
    public static ReviewReadDTO ReviewToReviewRead(Review review){

        // Create a new ReviewReadDTO object to store the converted data.
        ReviewReadDTO result = new ReviewReadDTO();

        // Copy various attributes from the Review entity to the ReviewReadDTO.
        result.setReviewId(review.getReviewId()); // Set review ID.
        result.setRating(review.getRating()); // Set rating.
        result.setComment(review.getComment()); // Set comment.
        result.setReviewTime(review.getReviewTime()); // Set review timestamp.
        result.setUserEmail(review.getUser().getEmail());

        // Set the book title by accessing the associated Book entity.
        result.setBookTitle(review.getBook().getTitle());
        // Set the user's full name by accessing the associated User entity.
        result.setUserFullName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        // Return the populated ReviewReadDTO.
        return result;
    }
}
