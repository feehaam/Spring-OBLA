package com.feeham.obla.service.interfaces;

import com.feeham.obla.entity.User;
import com.feeham.obla.exception.InvalidEntityException;
import com.feeham.obla.exception.ModelMappingException;
import com.feeham.obla.exception.UserNotFoundException;
import com.feeham.obla.model.borrow.BorrowReadDTO;
import com.feeham.obla.model.userdto.UserCreateDTO;
import com.feeham.obla.model.userdto.UserReadDTO;
import com.feeham.obla.model.userdto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    public void create(UserCreateDTO userCreateDTO) throws ModelMappingException, InvalidEntityException;
    public UserReadDTO readById(Long userId) throws ModelMappingException, UserNotFoundException;
    public UserReadDTO readByEmail(String email) throws ModelMappingException, UserNotFoundException;
    public User getUserEntityByEmail(String email) throws ModelMappingException, UserNotFoundException;;
    public List<UserReadDTO> readAll() throws ModelMappingException;
    public void update(UserUpdateDTO userUpdateDTO) throws ModelMappingException, InvalidEntityException, UserNotFoundException;
    public void delete(Long userId) throws UserNotFoundException;
    public List<String> getBorrowed(Long userId) throws UserNotFoundException;
    public List<String> getBorrowedCurrently(Long userId) throws UserNotFoundException;
    public List<BorrowReadDTO> getHistory(Long userId) throws UserNotFoundException;
}
