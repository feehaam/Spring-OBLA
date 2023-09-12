package com.feeham.obla.service.impl;

import com.feeham.obla.entity.User;
import com.feeham.obla.exception.UserNotFoundException;
import com.feeham.obla.repository.UserRepository;
import com.feeham.obla.service.interfaces.UserCredentialsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialServiceImpl implements UserCredentialsService {
    private final UserRepository userRepository;

    public UserCredentialServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long getUserId() {
        Optional<User> userOptional = userRepository.findByEmail(getUserNameFromToken());
        if(userOptional.isPresent()){
            return userOptional.get().getUserId();
        }
        throw new UserNotFoundException("User with username " + getUserNameFromToken() + " not found.", "Post authorizing user info.",
                "There is no user in database with email " + getUserNameFromToken());
    }

    public final String getUserNameFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
