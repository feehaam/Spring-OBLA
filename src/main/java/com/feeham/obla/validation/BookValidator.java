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
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("- Book ID must be non-negative");
        RULES.add("- Title must be at least 4 letters long");
        RULES.add("- The author's name must be at least 4 letters long");
        RULES.add("- ISBN must not be null");
        RULES.add("- Description must be at least 10 letters long");
    }

    public void validate(Book book) {
        List<String> violations = new ArrayList<>();

        validateBookId(book.getBookId(), violations);
        validateTitle(book.getTitle(), violations);
        validateAuthor(book.getAuthor(), violations);
        validateISBN(book.getIsbn(), violations);
        validateDescription(book.getDescription(), violations);

        if (!violations.isEmpty()) {
            throw new InvalidEntityException("Book", "Book validation failed", createValidationData(violations));
        }
    }

    private void validateBookId(Long bookId, List<String> violations) {
        if (bookId != null && bookId < 0) {
            violations.add(RULES.get(0));
        }
    }

    private void validateTitle(String title, List<String> violations) {
        if (title == null || title.length() < 4) {
            violations.add(RULES.get(1));
        }
    }

    private void validateAuthor(String author, List<String> violations) {
        if (author == null || author.length() < 4) {
            violations.add(RULES.get(2));
        }
    }

    private void validateISBN(String isbn, List<String> violations) {
        if (isbn == null) {
            violations.add(RULES.get(3));
        }
    }

    private void validateDescription(String description, List<String> violations) {
        if (description == null || description.length() < 10) {
            violations.add(RULES.get(4));
        }
    }

    private Map<String, List<String>> createValidationData(List<String> violations) {
        Map<String, List<String>> validationData = new HashMap<>();
        validationData.put("Rules", RULES);
        validationData.put("Violated", violations);
        return validationData;
    }
}
