package com.tmp.xmash.dto.request;


import com.tmp.xmash.type.Gender;
import com.tmp.xmash.type.RoleType;

/**
 *
 * @author sejin
 */
public record SignUpRequest(
    String userId,
    String password,
    String email,
    String userName,
    Gender gender,

    RoleType role
) {

}
