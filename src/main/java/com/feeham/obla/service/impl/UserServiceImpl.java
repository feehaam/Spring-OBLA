package com.feeham.obla.service.impl;

import com.feeham.obla.entity.Role;
import com.feeham.obla.entity.User;
import com.feeham.obla.exception.CustomException;
import com.feeham.obla.exception.UserNotFoundException;
import com.feeham.obla.model.userdto.UserCreateDTO;
import com.feeham.obla.model.userdto.UserReadDTO;
import com.feeham.obla.model.userdto.UserUpdateDTO;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.UserService;
import com.feeham.obla.validation.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userValidator = new UserValidator();
    }

    @Override
    public void create(UserCreateDTO userCreateDTO) {
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
    public User getUserEntityByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with email " + email + " not found.", "Searching for user by username",
                    "There is no user in database with email " + email);
        }
        return userOptional.get();
    }

    @Override
    public UserReadDTO readById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " not found.", "Searching for user by id",
                    "There is no user in database with id " + userId);
        }
        User user = userOptional.get();
        return modelMapper.map(user, UserReadDTO.class);
    }

    @Override
    public UserReadDTO readByEmail(String email) {
        return modelMapper.map(getUserEntityByEmail(email), UserReadDTO.class);
    }

    @Override
    public List<UserReadDTO> readAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserReadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
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
            throw new UserNotFoundException("User with id " + userId + " not found.", "Updaing user with id "+userId,
                    "There is no user in database with id " + userId);
        }
    }

    @Override
    public void delete(Long userId) {
        readById(userId);
        userRepository.deleteById(userId);
    }
}
