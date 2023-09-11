package com.feeham.obla.utilities.mapping;

import com.feeham.obla.entity.Review;
import com.feeham.obla.model.reviewdto.ReviewReadDTO;

public class ReviewConverter {
    public static ReviewReadDTO ReviewToReviewRead(Review review){
        ReviewReadDTO result = new ReviewReadDTO();
        result.setReviewId(review.getReviewId());
        result.setRating(review.getRating());
        result.setComment(review.getComment());
        result.setReviewTime(review.getReviewTime());
        result.setBookTitle(review.getBook().getTitle());
        result.setUserFullName(review.getUser().getFirstName() + " "
                + review.getUser().getLastName());
        return result;
    }
}
