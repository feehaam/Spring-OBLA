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
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("Rating must be between 1 and 5 (inclusive)");
    }

    public void validate(Review review) {
        List<String> violations = new ArrayList<>();
        validateRating(review.getRating(), violations);
        if (review.getComment() == null) {
            review.setComment("");
        }

        if (!violations.isEmpty()) {
            throw new InvalidEntityException("Review", "Review validation failed", createValidationData(violations));
        }
    }

    private void validateRating(double rating, List<String> violations) {
        if (rating < 1 || rating > 5) {
            violations.add(RULES.get(0));
        }
    }

    private Map<String, List<String>> createValidationData(List<String> violations) {
        Map<String, List<String>> validationData = new HashMap<>();
        validationData.put("Rules", RULES);
        validationData.put("Violated", violations);
        return validationData;
    }
}

