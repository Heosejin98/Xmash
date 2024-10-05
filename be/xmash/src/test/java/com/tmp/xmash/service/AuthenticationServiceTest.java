package com.tmp.xmash.service;

import com.tmp.xmash.type.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService loginService;

    @Test
    void signUp() {
        // given...
        String userId = "bgm";
        String password = "bgm";
        Gender gender = Gender.MALE;
        String name = "유근모";
        String email = "bgm@ex-em.com";

        // when
        loginService.signUp(userId,
                password,
                email,
                name,
                gender
        );
    }
}