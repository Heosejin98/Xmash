package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void login(String userId, String password) {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            throw new IllegalArgumentException("Password not matched");
        }

        passwordEncoder.encode(userId);
    }

    @Transactional
    public void signUp(String userId, String password) {
        userRepository.save(new AppUser(userId, passwordEncoder.encode(password)));
    }
}
