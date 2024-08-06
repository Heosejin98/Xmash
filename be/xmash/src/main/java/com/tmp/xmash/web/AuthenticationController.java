package com.tmp.xmash.web;

import com.tmp.xmash.model.LoginRequest;
import com.tmp.xmash.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public Boolean login(@RequestBody LoginRequest loginRequest) {

        return authenticationService.login(loginRequest);
    }

}
