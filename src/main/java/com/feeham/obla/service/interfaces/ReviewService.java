package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;
import com.feeham.obla.model.reviewdto.ReviewCreateDTO;
import com.feeham.obla.model.reviewdto.ReviewReadDTO;
import com.feeham.obla.model.reviewdto.ReviewUpdateDTO;

import java.util.List;

public interface ReviewService {
    public void create(ReviewCreateDTO reviewCreateDTO) throws ModelMappingException, InvalidEntityException, DatabaseException;
    public ReviewReadDTO readById(Long reviewId) throws ModelMappingException, DatabaseException, ReviewNotFoundException;
    public List<ReviewReadDTO> readAll() throws ModelMappingException, DatabaseException;
    public void update(ReviewUpdateDTO reviewUpdateDTO) throws ModelMappingException, InvalidEntityException, ReviewNotFoundException, DatabaseException;
    public void delete(Long reviewId) throws ReviewNotFoundException, DatabaseException;
}
