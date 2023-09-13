package com.feeham.obla.controller;

import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookUpdateDTO;
import com.feeham.obla.service.interfaces.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Creates a new book.
     *
     * @param bookCreateDTO The book information to create.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/books/create")
    public ResponseEntity<?> createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        bookService.create(bookCreateDTO);
        return ResponseEntity.ok("New book created.");
    }

    /**
     * Updates an existing book.
     *
     * @param bookId       The ID of the book to update.
     * @param bookUpdateDTO The updated book information.
     * @return A ResponseEntity with a success message.
     */
    @PutMapping("/books/update/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookUpdateDTO bookUpdateDTO) {
        bookUpdateDTO.setBookId(bookId);
        bookService.update(bookUpdateDTO);
        return ResponseEntity.ok("Book updated successfully.");
    }

    /**
     * Deletes a book by its ID.
     *
     * @param bookId The ID of the book to delete.
     * @return A ResponseEntity with the deleted book ID.
     */
    @DeleteMapping("/books/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return ResponseEntity.ok(bookId);
    }

    /**
     * Retrieves a list of all books.
     *
     * @return A ResponseEntity with a list of books.
     */
    @GetMapping("/books/all")
    public ResponseEntity<?> getAllBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();
            if (!roles.isEmpty()) {
                String role = roles.get(0);
                return ResponseEntity.ok(bookService.readAll(role));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }

    /**
     * Deletes a book by its ID.
     *
     * @param bookId The ID of the book to delete.
     * @return A ResponseEntity with a success message.
     */
    @DeleteMapping("/books/delete")
    public ResponseEntity<?> getBookById(@RequestBody Long bookId) {
        bookService.delete(bookId);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
