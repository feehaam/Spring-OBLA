package com.feeham.obla.service.interfaces;

import com.feeham.obla.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserCredentialsService {
    public Long getUserId();
}
