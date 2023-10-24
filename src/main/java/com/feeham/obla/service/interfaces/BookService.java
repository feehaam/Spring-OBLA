package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;
import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookReadDTOCustomer;
import com.feeham.obla.model.bookdto.BookUpdateDTO;

import java.util.List;

public interface BookService {
    public void create(BookCreateDTO bookCreateDTO) throws ModelMappingException, InvalidEntityException;
    public BookReadDTOCustomer readById(Long bookId, String role) throws ModelMappingException, BookNotFoundException;
    public List<?> readAll(String role) throws ModelMappingException;
    public void update(BookUpdateDTO bookUpdateDTO) throws ModelMappingException, InvalidEntityException, BookNotFoundException;
    public void delete(Long bookId) throws BookNotFoundException;
}
