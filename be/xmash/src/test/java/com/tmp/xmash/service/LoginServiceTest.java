package com.tmp.xmash.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    void signUp() {
        // given
        String userId = "test";
        String password = "test";

        // when
//        loginService.signUp(userId, password);

        // then
    }
}