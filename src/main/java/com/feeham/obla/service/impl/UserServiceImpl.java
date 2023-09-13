package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Role;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.CustomException;
import com.feeham.obla.exception.InvalidEntityException;
import com.feeham.obla.exception.ModelMappingException;
import com.feeham.obla.exception.UserNotFoundException;
import com.feeham.obla.model.borrow.BorrowReadDTO;
import com.feeham.obla.model.userdto.UserCreateDTO;
import com.feeham.obla.model.userdto.UserReadDTO;
import com.feeham.obla.model.userdto.UserUpdateDTO;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.UserService;
import com.feeham.obla.validation.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    @Override
    public void create(UserCreateDTO userCreateDTO)  throws ModelMappingException, InvalidEntityException {
        Optional<User> userOptional = userRepository.findByEmail(userCreateDTO.getEmail());
        if(userOptional.isPresent()) {
            throw new CustomException("DuplicateEntityException", "Can not create new account",
                    "Creating new user", "Email " + userCreateDTO.getEmail() + " is already registered");
        }
        User user = modelMapper.map(userCreateDTO, User.class);
        user.setRole(new Role(userCreateDTO.getRole()));
        userValidator.validate(user);
        userRepository.save(user);
    }

    @Override
    public User getUserEntityByEmail(String email)  throws ModelMappingException, UserNotFoundException{
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with email " + email + " not found.", "Searching for user by username",
                    "There is no user in database with email " + email);
        }
        return userOptional.get();
    }

    @Override
    public UserReadDTO readById(Long userId)  throws ModelMappingException, UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " not found.", "Searching for user by id",
                    "There is no user in database with id " + userId);
        }
        User user = userOptional.get();
        return modelMapper.map(user, UserReadDTO.class);
    }

    @Override
    public UserReadDTO readByEmail(String email)  throws ModelMappingException, UserNotFoundException{
        return modelMapper.map(getUserEntityByEmail(email), UserReadDTO.class);
    }

    @Override
    public List<UserReadDTO> readAll()  throws ModelMappingException{
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserReadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO)  throws ModelMappingException, InvalidEntityException, UserNotFoundException{
        Long userId = userUpdateDTO.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAddress(userUpdateDTO.getAddress());
            user.setPassword(userUpdateDTO.getPassword());
            user.setFirstName(userUpdateDTO.getFirstName());
            user.setLastName(userUpdateDTO.getLastName());
            user.setRole(new Role(userUpdateDTO.getRole().toUpperCase()));
            userValidator.validate(user);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with given id is not found.", "Updating user",
                    "There is no user in database with the provided id.");
        }
    }

    @Override
    public void delete(Long userId) throws UserNotFoundException{
        readById(userId);
        User user = userRepository.findById(userId).get();
        user.setArchived(true);
        userRepository.save(user);
    }

    @Override
    public List<String> getBorrowed(Long userId) throws UserNotFoundException {
        readById(userId);
        User user = userRepository.findById(userId).get();
        return user.getBorrows().stream().map(borrow -> {
            return borrow.getBorrowId() + ". " + borrow.getBook().getTitle() + " (" + borrow.getBorrowDate()+")";
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getBorrowedCurrently(Long userId) throws UserNotFoundException {
        readById(userId);
        User user = userRepository.findById(userId).get();
        return user.getBorrows().stream().filter(borrow -> {
            return borrow.getReturnDate() == null;
        }).map(borrow -> {
            return borrow.getBorrowId() + ". " + borrow.getBook().getTitle() + " (" + borrow.getBorrowDate()+")";
        }).collect(Collectors.toList());
    }

    @Override
    public List<BorrowReadDTO> getHistory(Long userId) throws UserNotFoundException {
        readById(userId);
        User user = userRepository.findById(userId).get();
        return user.getBorrows().stream().map(borrow -> {
            BorrowReadDTO result = new BorrowReadDTO();
            result.setBorrowId(borrow.getBorrowId());
            result.setBookId(borrow.getBook().getBookId());
            result.setBookTitle(borrow.getBook().getTitle());
            result.setDueDate(borrow.getDueDate());
            result.setReturnDate(borrow.getReturnDate());
            result.setBorrowDate(borrow.getBorrowDate());
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserEntityByEmail(email);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                true,true,true,true,
                roles);
    }
}
