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
        String userId = "test1";
        String password = "test1";

        // when
//        loginService.signUp(userId, password);

        // then
    }
}