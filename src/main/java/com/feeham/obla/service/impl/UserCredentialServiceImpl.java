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

    /**
     * Retrieves the user's ID based on the username obtained from the security context.
     *
     * @return The ID of the authenticated user.
     * @throws UserNotFoundException If the user is not found in the database.
     */
    @Override
    public Long getUserId() {
        Optional<User> userOptional = userRepository.findByEmail(getUserNameFromToken());
        if (userOptional.isPresent()) {
            return userOptional.get().getUserId();
        }
        throw new UserNotFoundException("User with username " + getUserNameFromToken() + " not found.", "Post authorizing user info.",
                "There is no user in the database with email " + getUserNameFromToken());
    }

    /**
     * Retrieves the username from the security context.
     *
     * @return The username of the authenticated user.
     */
    public final String getUserNameFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
