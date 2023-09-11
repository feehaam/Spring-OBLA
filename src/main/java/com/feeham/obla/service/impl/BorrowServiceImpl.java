package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Book;
import com.feeham.obla.entity.Borrow;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.*;
import com.feeham.obla.repository.BookRepository;
import com.feeham.obla.repository.BorrowRepository;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.BorrowService;
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

    public BorrowServiceImpl(BorrowRepository borrowRepository, BookRepository bookRepository, UserRepository userRepository, BorrowValidator borrowValidator) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowValidator = borrowValidator;
    }

    @Override
    public void create(Long bookId, Long userId) throws ModelMappingException, InvalidEntityException {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " not found.", "Borrowing a book",
                    "There is no user in database with id " + userId);
        }
        if(bookOptional.isEmpty() || bookOptional.get().getArchived()){
            throw new BookNotFoundException("Book not found", "Borrowing a book", "Book with ID " + bookId + " not found.");
        }
        Book book = bookOptional.get();
        if (!book.getAvailability()) {
            throw new CustomException("BookNotAvailableException", "Borrowing a book", "The book "+
                    book.getTitle() +" is not available", "Book is already borrowed by another customer.");
        }
        Borrow borrow = new Borrow();
        borrow.setBook(bookOptional.get());
        borrow.setUser(userOptional.get());
        borrow.setDueDate(LocalDate.now());

        borrowValidator.validate(borrow);
        borrowRepository.save(borrow);

        book.setAvailability(false);
        bookRepository.save(book);
    }

    @Override
    public void update(Long bookId, Long userId) throws ModelMappingException, InvalidEntityException, NotFoundException {
        List<Borrow> borrows = borrowRepository.findAll();
        for(Borrow borrow : borrows){
            if(borrow.getUser().getUserId() == userId && borrow.getReturnDate() == null){
                if(borrow.getUser().getUserId() != userId){
                    throw new InvalidEntityException("User with id " + userId + " not found.", "Updating a borrow",
                            "User with id " + userId + " is not the owner of the current borrow of book ID " + bookId);
                }
                Optional<Book> bookOptional = bookRepository.findById(borrow.getBook().getBookId());
                if(bookOptional.isEmpty()){
                    throw new BookNotFoundException("Book not found", "Borrowing a book", "Book with ID " + borrow.getBook().getBookId() + " not found.");
                }
                if(borrow.getReturnDate() != null){
                    throw new CustomException("BookReturnedException", "Can not return book",
                            "Returning a book", "The book " + borrow.getBook().getTitle() + " is already returned by you. .");
                }

                Book book = bookOptional.get();
                borrow.setReturnDate(LocalDate.now());
                borrowValidator.validate(borrow);
                borrowRepository.save(borrow);

                book.setAvailability(true);
                bookRepository.save(book);

                return;
            }
        }
        throw new NotFoundException("Book with id " + bookId + " not found.", "Returning book",
                "There is no borrowed book with id " + bookId);
    }
}
