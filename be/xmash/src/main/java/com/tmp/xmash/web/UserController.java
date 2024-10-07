package com.tmp.xmash.web;


import com.tmp.xmash.dto.request.PasswordUpdateRequest;
import com.tmp.xmash.dto.request.UserProfileRequest;
import com.tmp.xmash.dto.response.PlayerResponse;
import com.tmp.xmash.dto.response.UserProfileResponse;
import com.tmp.xmash.service.UserConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "User", description = "User 정보 관련 api")
public class UserController {

    private final UserConfigService userConfigService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMe(
            HttpSession session
    ) {
        String myId = (String) session.getAttribute("userId");
        System.out.println("myId = " + myId);

        return ResponseEntity.ok(userConfigService.getUserProfile(myId));
    }

    @Operation(summary = "모든 유저 조회", description = "초기화면에서 호출하여 검색 화면에서 자동완성되면서 보여줌")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 반환"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/users")
    public ResponseEntity<List<PlayerResponse>> getAllUser() {

        return ResponseEntity.ok(userConfigService.getUserInfos());
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경")
    @PatchMapping("/me/password")
    public ResponseEntity<UserProfileResponse> updateUserPassword(
            HttpSession session,
            @RequestBody PasswordUpdateRequest passwordUpdateRequest
    ) throws AuthenticationException, BadRequestException {
        String myId = (String) session.getAttribute("userId");

        if (myId == null) {
            throw new AuthenticationException("로그인 필요");
        }

        return ResponseEntity.ok(userConfigService.updateUserPassword(passwordUpdateRequest, myId));
    }

    @Operation(summary = "user 프로필 정보 변경", description = "내 정보 변경")
    @PatchMapping("/users/me")
    public ResponseEntity<UserProfileResponse> updateUserInfo(
            @RequestBody UserProfileRequest userProfileRequest
    ) {
        return ResponseEntity.ok(userConfigService.updateUserInfo(userProfileRequest));
    }

}
