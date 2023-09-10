package com.feeham.obla.service.interfaces;

import com.feeham.obla.exception.DatabaseException;
import com.feeham.obla.exception.InvalidEntityException;
import com.feeham.obla.exception.ModelMappingException;
import com.feeham.obla.exception.UserNotFoundException;
import com.feeham.obla.model.userdto.UserCreateDTO;
import com.feeham.obla.model.userdto.UserReadDTO;
import com.feeham.obla.model.userdto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    public void create(UserCreateDTO userCreateDTO) throws ModelMappingException, InvalidEntityException, DatabaseException;
    public UserReadDTO readById(Long userId) throws ModelMappingException, DatabaseException, UserNotFoundException;
    public UserReadDTO readByEmail(String email) throws ModelMappingException, DatabaseException, UserNotFoundException;
    public List<UserReadDTO> readAll() throws ModelMappingException, DatabaseException;
    public void update(UserUpdateDTO userUpdateDTO) throws ModelMappingException, InvalidEntityException, UserNotFoundException, DatabaseException;
    public void delete(Long userId) throws UserNotFoundException, DatabaseException;
}
