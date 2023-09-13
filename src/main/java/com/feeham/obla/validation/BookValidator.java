package com.feeham.obla.validation;

import com.feeham.obla.entity.Book;
import com.feeham.obla.exception.InvalidEntityException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookValidator {

    // Define a list of validation rules to explain the validation criteria.
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("- Book ID must be non-negative");
        RULES.add("- Title must be at least 4 letters long");
        RULES.add("- The author's name must be at least 4 letters long");
        RULES.add("- ISBN must not be null");
        RULES.add("- Description must be at least 10 letters long");
        RULES.add("- Description must be within 1000 characters");
    }

    // This method performs validation on a Book entity.
    public void validate(Book book) {
        List<String> violations = new ArrayList<>();

        // Validate individual attributes of the Book entity.
        validateBookId(book.getBookId(), violations);
        validateTitle(book.getTitle(), violations);
        validateAuthor(book.getAuthor(), violations);
        validateISBN(book.getIsbn(), violations);
        validateDescription(book.getDescription(), violations);

        // If there are violations, throw an InvalidEntityException with details.
        if (!violations.isEmpty()) {
            throw new InvalidEntityException("Book", "Book validation failed", createValidationData(violations));
        }
    }

    private void validateBookId(Long bookId, List<String> violations) {
        // Check if the book ID is non-negative, and if not, add a violation message.
        if (bookId != null && bookId < 0) {
            violations.add(RULES.get(0));
        }
    }

    private void validateTitle(String title, List<String> violations) {
        // Check if the title is at least 4 characters long, and if not, add a violation message.
        if (title == null || title.length() < 4) {
            violations.add(RULES.get(1));
        }
    }

    private void validateAuthor(String author, List<String> violations) {
        // Check if the author's name is at least 4 characters long, and if not, add a violation message.
        if (author == null || author.length() < 4) {
            violations.add(RULES.get(2));
        }
    }

    private void validateISBN(String isbn, List<String> violations) {
        // Check if the ISBN is null, and if so, add a violation message.
        if (isbn == null) {
            violations.add(RULES.get(3));
        }
    }

    private void validateDescription(String description, List<String> violations) {
        // Check if the description is at least 10 characters long, and if not, add a violation message.
        if (description == null || description.length() < 10) {
            violations.add(RULES.get(4));
        }

        // Check if the description is within 1000 characters, and if not, add a violation message.
        if (description != null && description.length() > 1000) {
            violations.add(RULES.get(5));
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
