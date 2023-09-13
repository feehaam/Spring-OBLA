package com.feeham.obla.validation;

import com.feeham.obla.entity.Borrow;
import com.feeham.obla.exception.InvalidEntityException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class BorrowValidator {

    // Define a list of validation rules to explain the validation criteria.
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("- Borrow date cannot be null and must be a valid date.");
        RULES.add("- Return date must not be before the borrow date.");
        RULES.add("- Due date cannot be null and must not be before the borrow date.");
    }

    // This method performs validation on a Borrow entity.
    public void validate(Borrow borrow) {
        List<String> violations = new ArrayList<>();

        // Validate individual attributes of the Borrow entity.
        validateBorrowDate(borrow.getBorrowDate(), violations);
        validateReturnDate(borrow.getBorrowDate(), borrow.getReturnDate(), violations);
        validateDueDate(borrow.getBorrowDate(), borrow.getDueDate(), violations);

        // If there are violations, throw an InvalidEntityException with details.
        if (!violations.isEmpty()) {
            throw new InvalidEntityException("Borrow", "Borrow validation failed", createValidationData(violations));
        }
    }

    private void validateBorrowDate(LocalDate borrowDate, List<String> violations) {
        LocalDate currentDate = LocalDate.now();

        // Check if the borrow date is null or before the current date, and if so, add a violation message.
        if (borrowDate == null || borrowDate.isBefore(currentDate)) {
            violations.add(RULES.get(0));
        }
    }

    private void validateReturnDate(LocalDate borrowDate, LocalDate returnDate, List<String> violations) {
        // Check if the return date is not before the borrow date, and if so, add a violation message.
        if (returnDate != null && returnDate.isBefore(borrowDate)) {
            violations.add(RULES.get(1));
        }
    }

    private void validateDueDate(LocalDate borrowDate, LocalDate dueDate, List<String> violations) {
        // Check if the due date is null or before the borrow date, and if so, add a violation message.
        if (dueDate == null || dueDate.isBefore(borrowDate)) {
            violations.add(RULES.get(2));
        }
    }

    // Create a map containing validation data for reporting.
    private Map<String, List<String>> createValidationData(List<String> violations) {
        Map<String, List<String>> validationData = new HashMap<>();
        validationData.put("Rules", RULES);  // List of validation rules.
        validationData.put("Violated", violations);  // List of violated rules.
        return validationData;
    }
}
