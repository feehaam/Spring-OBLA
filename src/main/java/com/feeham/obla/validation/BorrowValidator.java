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
        RULES.add("- Due date cannot be null and must be a valid date");
        RULES.add("- Return date must be after the due date");
    }

    public void validate(Borrow borrow) {
        List<String> violations = new ArrayList<>();

        validateDueDate(borrow.getDueDate(), violations);
        validateReturnDate(borrow.getDueDate(), borrow.getReturnDate(), violations);

        if (!violations.isEmpty()) {
            throw new InvalidEntityException("Borrow", "Borrow validation failed", createValidationData(violations));
        }
    }

    private void validateDueDate(LocalDate dueDate, List<String> violations) {
        LocalDate currentDate = LocalDate.now();
        if (dueDate == null || dueDate.isBefore(currentDate)) {
            violations.add(RULES.get(0));
        }
    }

    private void validateReturnDate(LocalDate dueDate, LocalDate returnDate, List<String> violations) {
        if (returnDate != null && returnDate.isBefore(dueDate)) {
            violations.add(RULES.get(1));
        }
    }

    private Map<String, List<String>> createValidationData(List<String> violations) {
        Map<String, List<String>> validationData = new HashMap<>();
        validationData.put("Rules", RULES);
        validationData.put("Violated", violations);
        return validationData;
    }
}
