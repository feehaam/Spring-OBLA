package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;
import com.feeham.obla.model.borrow.BorrowCreateDTO;
import com.feeham.obla.model.borrow.BorrowReadDTO;
import com.feeham.obla.model.borrow.BorrowUpdateDTO;

import java.util.List;

public interface BorrowService{
    public void create(BorrowCreateDTO borrowCreateDTO) throws ModelMappingException, InvalidEntityException;
    public BorrowReadDTO readById(Long borrowId) throws ModelMappingException, NotFoundException;
    public List<BorrowReadDTO> readAll() throws ModelMappingException;
    public void update(BorrowUpdateDTO borrowUpdateDTO) throws ModelMappingException, InvalidEntityException, NotFoundException;
    public void delete(Long borrowId) throws NotFoundException;
}
