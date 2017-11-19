package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
