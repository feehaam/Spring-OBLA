package com.feeham.obla.validation;

import com.feeham.obla.entity.Borrow;
import com.feeham.obla.exception.InvalidEntityException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BorrowValidator {
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("- Borrow date cannot be null and must be a valid date.");
        RULES.add("- Return date must not be before borrow date.");
        RULES.add("- Borrow date cannot be null and must not be before borrow date.");
    }

    public void validate(Borrow borrow) {
        List<String> violations = new ArrayList<>();

        validateBorrowDate(borrow.getBorrowDate(), violations);
        validateReturnDate(borrow.getBorrowDate(), borrow.getReturnDate(), violations);
        validateDueDate(borrow.getBorrowDate(), borrow.getDueDate(), violations);

        if (!violations.isEmpty()) {
            throw new InvalidEntityException("Borrow", "Borrow validation failed", createValidationData(violations));
        }
    }

    private void validateBorrowDate(LocalDate borrowDate, List<String> violations) {
        LocalDate currentDate = LocalDate.now();
        if (borrowDate == null || borrowDate.isBefore(currentDate)) {
            violations.add(RULES.get(0));
        }
    }

    private void validateReturnDate(LocalDate borrowDate, LocalDate returnDate, List<String> violations) {
        if (returnDate != null && returnDate.isBefore(borrowDate)) {
            violations.add(RULES.get(1));
        }
    }

    private void validateDueDate(LocalDate borrowDate, LocalDate dueDate, List<String> violations) {
        if (dueDate == null || dueDate.isBefore(borrowDate)) {
            violations.add(RULES.get(2));
        }
    }

    private Map<String, List<String>> createValidationData(List<String> violations) {
        Map<String, List<String>> validationData = new HashMap<>();
        validationData.put("Rules", RULES);
        validationData.put("Violated", violations);
        return validationData;
    }
}
