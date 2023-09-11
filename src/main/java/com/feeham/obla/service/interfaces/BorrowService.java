package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;

import java.util.List;

public interface BorrowService{
    public void create(Long bookId, Long userId) throws ModelMappingException, InvalidEntityException;
    public void update(Long borrowId, Long userId) throws ModelMappingException, InvalidEntityException, NotFoundException;
}
