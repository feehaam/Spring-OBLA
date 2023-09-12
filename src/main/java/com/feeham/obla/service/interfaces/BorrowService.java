package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;

import java.time.LocalDate;
import java.util.List;

public interface BorrowService{
    public void create(Long bookId, Long userId, LocalDate dueDate) throws ModelMappingException, InvalidEntityException;
    public void update(Long borrowId, Long userId) throws ModelMappingException, InvalidEntityException, NotFoundException;
}
