package com.tmp.xmash.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService loginService;

    @Test
    void signUp() {
        // given
        String userId = "se1";
        String password = "qwe123";

        // when
//        loginService.signUp(userId, password);
        // then
    }
}