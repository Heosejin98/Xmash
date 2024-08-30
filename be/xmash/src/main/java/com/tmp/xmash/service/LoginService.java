package com.tmp.xmash.service;

import com.tmp.xmash.db.repositroy.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public boolean login(String userId, String password) {
        return userRepository.findByUserId(userId)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
}
