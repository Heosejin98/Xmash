package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.LoginRequest;
import com.tmp.xmash.dto.response.UserProfileResponse;
import com.tmp.xmash.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication 전적 관련 api")
public class AuthenticationController {

    private final AuthenticationService loginService;

    @PostMapping("/login")
    public ResponseEntity<UserProfileResponse> loginPost(
            HttpSession session,
            @RequestBody LoginRequest loginRequest
    ) {
        try {
            UserProfileResponse userInfoResponse = loginService.login(loginRequest.userId(), loginRequest.password());
            session.setAttribute("userId", loginRequest.userId());

            return ResponseEntity.ok(userInfoResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 로그인 실패 메시지;
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<Boolean> logoutPost(
            HttpSession session
    ) {

        return ResponseEntity.ok(Boolean.TRUE);
    }

}
