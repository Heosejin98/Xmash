package com.tmp.xmash.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private final String userId;
    private final String password;
}
