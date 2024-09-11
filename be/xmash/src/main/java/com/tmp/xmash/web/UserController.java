package com.tmp.xmash.web;


import com.tmp.xmash.dto.response.UserInfoResponse;
import com.tmp.xmash.service.UserConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "User", description = "User 정보 관련 api")
public class UserController {

    private final UserConfigService userConfigService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMe(
            HttpSession session
    ) {
        String myId = (String) session.getAttribute("userId");

        return ResponseEntity.ok(userConfigService.getUserInfo(myId));
    }
}
