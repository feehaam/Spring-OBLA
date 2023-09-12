package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Book;
import com.feeham.obla.entity.Borrow;
import com.feeham.obla.entity.Reserve;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.BookNotFoundException;
import com.feeham.obla.exception.CustomException;
import com.feeham.obla.exception.UserNotFoundException;
import com.feeham.obla.repository.BookRepository;
import com.feeham.obla.repository.ReservationRepository;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Override
    public void reserve(Long userId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book not found", "Requesting reservation", "Book with ID " + bookId + " not found.");
        }
        Book book = bookOptional.get();
        for(Reserve reserve: book.getReserves()){
            if(reserve.getUser().getUserId() == userId){
                throw new CustomException("BookReservationException", "Requesting reservation",  "Book already reserved",
                        "Book with ID " + bookId + " is already reserved by user with id " + userId);
            }
        }
        for(Borrow borrow: book.getBorrows()){
            if(borrow.getUser().getUserId() == userId){
                throw new CustomException("BookReservationException", "Requesting reservation",  "Book already borrowed",
                        "The requested book " + book.getTitle() + " is already borrowed by the requested user " + borrow.getUser().getFirstName());
            }
        }
        if(book.getAvailability()){
            throw new CustomException("BookReservationException", "Requesting reservation",  "Can not reserve an available book",
                    "Book with ID " + bookId + " is available and can be directly borrowed.");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " not found.", "Requesting reservation",
                    "There is no user in database with id " + userId);
        }
        User user = userOptional.get();
        Reserve reserve = new Reserve();
        reserve.setBook(book);
        reserve.setUser(user);
        reserve.setReserveDateTime(LocalDateTime.now());

        reservationRepository.save(reserve);
    }

    @Override
    public void cancel(Long userId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book not found", "Requesting reservation", "Book with ID " + bookId + " not found.");
        }
        Book book = bookOptional.get();
        for(Reserve reserve: book.getReserves()){
            if(reserve.getUser().getUserId() == userId){
                reservationRepository.delete(reserve);
                return;
            }
        }
        throw new CustomException("BookReservationException", "Book not reserved",  "Requesting reservation",
                "Book with ID " + bookId + " is not reserved by user with id " + userId);
    }
}
