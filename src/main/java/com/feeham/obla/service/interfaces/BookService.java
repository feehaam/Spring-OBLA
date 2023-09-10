package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.*;
import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookReadDTO;
import com.feeham.obla.model.bookdto.BookUpdateDTO;

import java.util.List;

public interface BookService {
    public void create(BookCreateDTO bookCreateDTO) throws ModelMappingException, InvalidEntityException, DatabaseException;
    public BookReadDTO readById(Long bookId) throws ModelMappingException, DatabaseException, BookNotFoundException;
    public List<BookReadDTO> readAll() throws ModelMappingException, DatabaseException;
    public void update(BookUpdateDTO bookUpdateDTO) throws ModelMappingException, InvalidEntityException, BookNotFoundException, DatabaseException;
    public void delete(Long bookId) throws BookNotFoundException, DatabaseException;
}
