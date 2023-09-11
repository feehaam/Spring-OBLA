package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;
import com.feeham.obla.model.reviewdto.ReviewCreateDTO;
import com.feeham.obla.model.reviewdto.ReviewReadDTO;
import com.feeham.obla.model.reviewdto.ReviewUpdateDTO;

import java.util.List;

public interface ReviewService {
    public void create(Long bookId, Long userId, ReviewCreateDTO reviewCreateDTO) throws ModelMappingException, InvalidEntityException, BookNotFoundException, UserNotFoundException ;
    public ReviewReadDTO readById(Long reviewId) throws ModelMappingException, ReviewNotFoundException;
    public List<ReviewReadDTO> readAll() throws ModelMappingException;
    public void update(Long reviewId, Long bookId, Long userId, ReviewUpdateDTO reviewUpdateDTO) throws ModelMappingException, InvalidEntityException, ReviewNotFoundException;
    public void delete(Long userId, Long reviewId) throws ReviewNotFoundException;
    public List<ReviewReadDTO> findByBookId(Long bookId) throws ModelMappingException, BookNotFoundException;
}
