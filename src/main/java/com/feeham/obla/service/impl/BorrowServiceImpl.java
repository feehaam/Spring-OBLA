package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Book;
import com.feeham.obla.entity.Borrow;
import com.feeham.obla.entity.Reserve;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.*;
import com.feeham.obla.repository.BookRepository;
import com.feeham.obla.repository.BorrowRepository;
import com.feeham.obla.repository.ReservationRepository;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.BorrowService;
import com.feeham.obla.utilities.notification.NotificationService;
import com.feeham.obla.validation.BorrowValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServiceImpl implements BorrowService {

    private BorrowRepository borrowRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BorrowValidator borrowValidator;
    private ReservationRepository reservationRepository;

    public BorrowServiceImpl(BorrowRepository borrowRepository, BookRepository bookRepository, UserRepository userRepository, BorrowValidator borrowValidator, ReservationRepository reservationRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowValidator = borrowValidator;
        this.reservationRepository = reservationRepository;
    }

    /**
     * Creates a new borrow record for a book.
     *
     * @param bookId   The ID of the book to borrow.
     * @param userId   The ID of the user borrowing the book.
     * @param dueDate  The due date for returning the book.
     * @throws ModelMappingException   If there is an issue with model mapping.
     * @throws InvalidEntityException If the entity is invalid.
     */
    @Override
    public void create(Long bookId, Long userId, LocalDate dueDate) throws ModelMappingException, InvalidEntityException {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("The expected user is not found.", "Borrowing a book",
                    "There is no user in the database with the given id.");
        }
        if (bookOptional.isEmpty() || bookOptional.get().getArchived()) {
            throw new BookNotFoundException("Book not found", "Borrowing a book", "Book with the given id is not found in the database.");
        }
        Book book = bookOptional.get();
        if (!book.getAvailability()) {
            throw new CustomException("BookNotAvailableException", "Borrowing a book", "The book " +
                    book.getTitle() + " is not available", "Book is already borrowed by another customer.");
        }
        Borrow borrow = new Borrow();
        borrow.setBook(bookOptional.get());
        borrow.setUser(userOptional.get());
        borrow.setDueDate(dueDate);
        borrow.setBorrowDate(LocalDate.now());

        borrowValidator.validate(borrow);
        borrowRepository.save(borrow);

        book.setAvailability(false);
        bookRepository.save(book);
    }

    /**
     * Updates a borrow record for returning a book.
     *
     * @param bookId The ID of the book being returned.
     * @param userId The ID of the user returning the book.
     * @throws ModelMappingException If there is an issue with model mapping.
     * @throws InvalidEntityException If the entity is invalid.
     * @throws NotFoundException If the book or borrow record is not found.
     */
    @Override
    public void update(Long bookId, Long userId) throws ModelMappingException, InvalidEntityException, NotFoundException {
        List<Borrow> borrows = borrowRepository.findAll();
        for (Borrow borrow : borrows) {
            if (borrow.getUser().getUserId() == userId && borrow.getBook().getBookId() == bookId && borrow.getReturnDate() == null) {
                Optional<Book> bookOptional = bookRepository.findById(borrow.getBook().getBookId());
                if (bookOptional.isEmpty()) {
                    throw new BookNotFoundException("Book not found", "Returning a book", "Book with the given id is not found.");
                }
                if (borrow.getReturnDate() != null) {
                    throw new CustomException("BookReturnedException", "Can not return book",
                            "Returning a book", "The book " + borrow.getBook().getTitle() + " is already returned by the customer.");
                }

                Book book = bookOptional.get();
                borrow.setReturnDate(LocalDate.now());
                book.getReserves().forEach(this::notify);
                reservationRepository.deleteByBookId(bookId);
                borrowRepository.save(borrow);
                book.setAvailability(true);
                bookRepository.save(book);

                return;
            }
        }
        throw new NotFoundException("Book with the given id is not found.", "Returning book",
                "There is no borrowed book with the given user id in the database");
    }

    /**
     * Notifies users about book availability.
     *
     * @param reserve The reserve entity.
     */
    private void notify(Reserve reserve) {
        Long userId = reserve.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return;
        }
        NotificationService.notifyBookAvailability(userOptional.get().getEmail());
    }
}
