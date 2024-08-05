package com.tmp.xmash.service;


import com.tmp.xmash.db.entity.User;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.model.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    public boolean login(LoginRequest loginRequest) {
        //TODO : 에러 AOP 만들기
        User user = userRepository.findByUserId(loginRequest.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return true; // 로그인 성공
        }

        throw new RuntimeException("Invalid password");
    }

}