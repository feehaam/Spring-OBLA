package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;
import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookReadDTOAdmin;
import com.feeham.obla.model.bookdto.BookUpdateDTO;

import java.util.List;

public interface BookService {
    public void create(BookCreateDTO bookCreateDTO) throws ModelMappingException, InvalidEntityException;
    public BookReadDTOAdmin readById(Long bookId) throws ModelMappingException, BookNotFoundException;
    public List<?> readAll(String role) throws ModelMappingException;
    public void update(BookUpdateDTO bookUpdateDTO) throws ModelMappingException, InvalidEntityException, BookNotFoundException;
    public void delete(Long bookId) throws BookNotFoundException;
}
