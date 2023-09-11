package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Book;
import com.feeham.obla.entity.Review;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.*;
import com.feeham.obla.model.reviewdto.ReviewCreateDTO;
import com.feeham.obla.model.reviewdto.ReviewReadDTO;
import com.feeham.obla.model.reviewdto.ReviewUpdateDTO;
import com.feeham.obla.repository.BookRepository;
import com.feeham.obla.repository.ReviewRepository;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.ReviewService;
import com.feeham.obla.utilities.mapping.ReviewConverter;
import com.feeham.obla.validation.ReviewValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewValidator reviewValidator;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewValidator reviewValidator, ModelMapper modelMapper, UserRepository userRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewValidator = reviewValidator;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void create(Long bookId, Long userId, ReviewCreateDTO reviewCreateDTO) throws ModelMappingException,
            InvalidEntityException, BookNotFoundException, UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " not found.", "Searching for user by id",
                    "There is no user in database with id " + userId);
        }
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Book not found", "Get book by ID", "Book with ID " + bookId + " not found.");
        }
        for(Review review: bookOptional.get().getReviews()){
            if(review.getUser().getUserId().equals(userId)){
                throw new CustomException("DuplicateEntityException",
                        "Try to edit existing review, review id: " + review.getReviewId(),
                        "Can not add multiple review",
                        "User with id " + userId + " already reviewed this book.");
            }
        }
        Review review = new Review();
        review.setRating(reviewCreateDTO.getRating());
        review.setComment(reviewCreateDTO.getComment());
        review.setUser(userOptional.get());
        review.setBook(bookOptional.get());
        review.setReviewTime(LocalDateTime.now());

        reviewValidator.validate(review);
        reviewRepository.save(review);
    }

    @Override
    public ReviewReadDTO readById(Long reviewId) throws ModelMappingException, ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(reviewOptional.isEmpty()){
            throw new ReviewNotFoundException("Review with id " + reviewId + " not found.", "Searching for review by id",
                    "There is no review in database with id " + reviewId);
        }
        return ReviewConverter.ReviewToReviewRead(reviewOptional.get());
    }

    @Override
    public List<ReviewReadDTO> readAll() throws ModelMappingException {
        return reviewRepository.findAll().stream()
                .map(review -> {
                    return ReviewConverter.ReviewToReviewRead(review);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void update(Long reviewId, Long bookId, Long userId, ReviewUpdateDTO reviewUpdateDTO) throws ModelMappingException, InvalidEntityException, ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(reviewOptional.isEmpty()){
            throw new ReviewNotFoundException("Review with id " + reviewId + " not found.",
                    "Updating review",
                    "There is no review in database with id " + reviewId);
        }
        Review review = reviewOptional.get();
        if(review.getUser().getUserId() != userId){
            throw new CustomException("InvalidEntityException", "Can not edit review",
                    "Attemp to edit review with id " + review,
                    "User with id " + userId + " isn't owner of this review hence doesn't have the privillage to update.");
        }
        if(review.getBook().getBookId()!= bookId){
            throw new CustomException("InvalidEntityException", "Can not edit review",
                    "Attempt to edit review with id " + review.getReviewId(),
                    "Book with id " + bookId + " doesn't conttain the expcected review.");
        }

        review.setRating(reviewUpdateDTO.getRating());
        review.setComment(reviewUpdateDTO.getComment());
        reviewRepository.save(review);
    }

    @Override
    public void delete(Long userId, Long reviewId) throws ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(reviewOptional.isEmpty()){
            throw new ReviewNotFoundException("Review with id " + reviewId + " not found.",
                    "Deleting review",
                    "There is no review in database with id " + reviewId);
        }
        Review review = reviewOptional.get();
        if(review.getUser().getUserId() != userId){
            throw new CustomException("InvalidEntityException", "Can not edit review",
                    "Attempt to delete review with id " + review.getReviewId(),
                    "User with id " + userId + " isn't owner of this review hence doesn't have the privillage to update.");
        }
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewReadDTO> findByBookId(Long bookId) throws ModelMappingException, BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Book not found", "Get reviews by book", "Book with ID " + bookId + " not found.");
        }
        return bookOptional.get().getReviews().stream()
                .map( review -> ReviewConverter.ReviewToReviewRead(review))
                .collect(Collectors.toList());
    }


}
