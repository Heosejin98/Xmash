package com.tmp.xmash.web;

import com.tmp.xmash.dto.LoginRequest;
import com.tmp.xmash.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> loginPage(HttpSession session, @RequestBody LoginRequest loginRequest) {
        try {
            String token = loginService.login(loginRequest.userId(), loginRequest.password());
            session.setAttribute("userId", token);  //

            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 로그인 실패 메시지;
        }
    }

}
