package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Book;
import com.feeham.obla.entity.Borrow;
import com.feeham.obla.entity.Reserve;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.*;
import com.feeham.obla.model.reservedto.ReserveReadDTO;
import com.feeham.obla.repository.BookRepository;
import com.feeham.obla.repository.ReservationRepository;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Reserves a book for a user.
     *
     * @param userId The ID of the user making the reservation.
     * @param bookId The ID of the book to be reserved.
     * @throws BookNotFoundException If the book is not found.
     * @throws CustomException      If the book is not available, already reserved, or already borrowed.
     * @throws UserNotFoundException If the user is not found.
     */
    @Override
    public void reserve(Long userId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book not found", "Requesting reservation", "Book with ID " + bookId + " not found.");
        }
        Book book = bookOptional.get();
        for (Reserve reserve : book.getReserves()) {
            if (reserve.getUserId() == userId) {
                throw new CustomException("BookReservationException", "Requesting reservation", "Book already reserved",
                        "Book with ID " + bookId + " is already reserved by user with ID " + userId);
            }
        }
//        for (Borrow borrow : book.getBorrows()) {
//            if (borrow.getUser().getUserId() == userId) {
//                throw new CustomException("BookReservationException", "Requesting reservation", "Book already borrowed",
//                        "The requested book " + book.getTitle() + " is already borrowed by the requested user " + borrow.getUser().getFirstName());
//            }
//        }
        if (book.getAvailability()) {
            throw new CustomException("BookReservationException", "Requesting reservation", "Can not reserve an available book",
                    "Book with ID " + bookId + " is available and can be directly borrowed.");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found.", "Requesting reservation",
                    "There is no user in the database with ID " + userId);
        }
        User user = userOptional.get();
        Reserve reserve = new Reserve();
        reserve.setBook(book);
        reserve.setUserId(user.getUserId());
        reserve.setReserveDateTime(LocalDateTime.now());

        reservationRepository.save(reserve);
    }

    /**
     * Cancels a reservation for a book by a user.
     *
     * @param userId The ID of the user canceling the reservation.
     * @param bookId The ID of the book to cancel the reservation for.
     * @throws BookNotFoundException  If the book is not found.
     * @throws CustomException       If the book is not reserved by the user.
     */
    @Transactional
    public void cancel(Long userId, Long bookId) {
        // Check if the book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found", "Requesting reservation", "Book with given ID is not found."));

        Reserve reserve = book.getReserves().stream()
                .filter(res -> res.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new CustomException("BookReservationException", "Book not reserved", "Requesting reservation",
                        "The target book is not reserved by the user"));

        reservationRepository.deleteByBookIdAndUserId(bookId, userId);
    }

    @Override
    public List<ReserveReadDTO> getByUser(Long userId) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getUserId() == userId)
                .map(r -> {
                    return new ReserveReadDTO(r.getId(), r.getReserveDateTime(), r.getBook().getBookId(), r.getBook().getTitle(), r.getBook().getImgUrl());
                })
                .collect(Collectors.toList());
    }
}
