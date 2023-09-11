package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;
import com.feeham.obla.model.borrow.BorrowCreateDTO;
import com.feeham.obla.model.borrow.BorrowReadDTO;
import com.feeham.obla.model.borrow.BorrowUpdateDTO;

import java.util.List;

public interface BorrowService{
    public void create(Long bookId, Long userId) throws ModelMappingException, InvalidEntityException;
    public void update(Long borrowId, Long userId) throws ModelMappingException, InvalidEntityException, NotFoundException;
}
