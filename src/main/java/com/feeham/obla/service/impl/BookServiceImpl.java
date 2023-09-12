package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Book;
import com.feeham.obla.exception.BookNotFoundException;
import com.feeham.obla.exception.CustomException;
import com.feeham.obla.exception.InvalidEntityException;
import com.feeham.obla.exception.ModelMappingException;
import com.feeham.obla.model.bookdto.BookCreateDTO;
import com.feeham.obla.model.bookdto.BookReadDTOAdmin;
import com.feeham.obla.model.bookdto.BookReadDTOCustomer;
import com.feeham.obla.model.bookdto.BookUpdateDTO;
import com.feeham.obla.repository.BookRepository;
import com.feeham.obla.service.interfaces.BookService;
import com.feeham.obla.utilities.enums.Roles;
import com.feeham.obla.validation.BookValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final BookValidator bookValidator;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.bookValidator = bookValidator;
    }

    @Override
    public void create(BookCreateDTO bookCreateDTO) throws ModelMappingException, InvalidEntityException {
        Book book = modelMapper.map(bookCreateDTO, Book.class);
        if(bookRepository.findByIsbn(book.getIsbn()).isPresent()){
            throw new CustomException("DuplicateEntityException", "Can not create new book",
                    "Creating new book", "Book with ISBN " + bookCreateDTO.getIsbn() + " conflicts.");
        }
        book.setAvailability(true);
        book.setArchived(false);
        bookValidator.validate(book);
        bookRepository.save(book);
    }

    @Override
    public BookReadDTOAdmin readById(Long bookId) throws ModelMappingException, BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Book not found", "Get book by ID", "Book with ID " + bookId + " not found.");
        }
        Book book = bookOptional.get();
        BookReadDTOAdmin bookReadDTO = modelMapper.map(book, BookReadDTOAdmin.class);
        if(book.getAvailability()) bookReadDTO.setStatus("Available");
        else bookReadDTO.setStatus("Taken");
        return bookReadDTO;
    }

    @Override
    public List<?> readAll(String role) throws ModelMappingException {
        if (role.contains(Roles.ADMIN.toString()))
            return bookRepository.findAll().stream()
                .map(book -> {
                    BookReadDTOAdmin result = modelMapper.map(book, BookReadDTOAdmin.class);
                    result.setStatus(book.getAvailability() ? "Available" : "Taken");
                    result.setDeleted(book.getArchived() ? "Yes" : "No");
                    return result;
                })
                .collect(Collectors.toList());
        else return bookRepository.findAll().stream()
                .map(book -> {
                    BookReadDTOCustomer result = modelMapper.map(book, BookReadDTOCustomer.class);
                    result.setStatus(book.getAvailability() ? "Available" : "Taken");
                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void update(BookUpdateDTO bookUpdateDTO) throws ModelMappingException, InvalidEntityException, BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookUpdateDTO.getBookId());
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Book not found", "Update book", "Book with ID " + bookUpdateDTO.getBookId() + " doesn't exist.");
        }
        Book book = bookOptional.get();
        book.setAuthor(bookUpdateDTO.getAuthor());
        book.setTitle(bookUpdateDTO.getTitle());
        book.setDescription(bookUpdateDTO.getDescription());
        bookRepository.save(book);
    }

    @Override
    public void delete(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Book not found", "Delete book", "Book with ID " + bookId + " doesn't exist.");
        }
        Book book = bookOptional.get();
        if(book.getArchived()) {
            throw new BookNotFoundException("Book not found", "Delete book", "Book with ID " + bookId + " is already archived.");
        }
        book.setArchived(true);
        bookRepository.save(book);
    }
}
