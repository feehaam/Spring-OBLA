package com.feeham.obla.validation;

import com.feeham.obla.entity.Review;
import com.feeham.obla.exception.InvalidEntityException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReviewValidator {

    // Define a list of validation rules to explain the validation criteria.
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("Rating must be between 1 and 5 (inclusive)");
    }

    // This method performs validation on a Review entity.
    public void validate(Review review) {
        List<String> violations = new ArrayList<>();

        // Validate the rating attribute of the Review entity.
        validateRating(review.getRating(), violations);

        // If the comment is null, set it to an empty string.
        if (review.getComment() == null) {
            review.setComment("");
        }

        // If there are violations, throw an InvalidEntityException with details.
        if (!violations.isEmpty()) {
            throw new InvalidEntityException("Review", "Review validation failed", createValidationData(violations));
        }
    }

    private void validateRating(Integer rating, List<String> violations) {
        // Check if the rating is not between 1 and 5 (inclusive), and if so, add a violation message.
        if (rating < 1 || rating > 5) {
            violations.add(RULES.get(0));
        }
    }

    // Create a map containing validation data for reporting.
    private String createValidationData(List<String> violations) {
        StringBuilder problems = new StringBuilder();
        if(violations.isEmpty()) return problems.toString();
        for(String v: violations) problems.append(v).append(", ");
        return problems.substring(0, problems.toString().length() - 2);
    }
}
