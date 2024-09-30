package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.PostTeamRequest;
import com.tmp.xmash.dto.response.UserProfileResponse;
import com.tmp.xmash.service.TeamRankingService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden    // Swagger에서 제외 안쓸듯.... 나중에 바뀔 수도 있음
@RestController
@RequestMapping("/rank")
@Tag(name = "Rank Game", description = "Rank Game 관련 api")
@RequiredArgsConstructor
public class RankController {

    private final TeamRankingService teamRankingService;

    @Operation(summary = "Double Rank Status 조회", description = "true = 팀 있음, false = 팀 없음")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Double Rank 상태를 성공적으로 반환"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/team/status")
    public ResponseEntity<Boolean> getDoubleRankTeamStatus(
            HttpSession session
    ) {
        String homeId = (String) session.getAttribute("userId");

        return ResponseEntity.ok(teamRankingService.hasTeam(homeId));
    }

    @Operation(summary = "팀에 속하지 않은 플레이어 조회", description = "복식 게임에서 팀에 속하지 않은 플레이어 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀에 속하지 않은 플레이어 목록을 성공적으로 반환"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/users/without-team")
    public ResponseEntity<List<UserProfileResponse>> getPlayersWithoutTeam() {
        List<UserProfileResponse> userProfileResponses = teamRankingService.getUsersWithoutTeam();

        return ResponseEntity.ok(userProfileResponses);
    }

    @Operation(summary = "팀 등록", description = "새로운 팀을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/team")
    public ResponseEntity<UserProfileResponse> postTeam(
            @RequestBody PostTeamRequest postTeamRequest
    ) {
        return ResponseEntity.ok(teamRankingService.postTeam(postTeamRequest));
    }

    @Operation(summary = "나의 팀원 정보 조회", description = "(Mock) 복식 게임 등록 시 나의 팀원 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "나의 팀원 아이디 반환"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/team/my")
    public ResponseEntity<UserProfileResponse> getMyTeamId(
            HttpSession session
    ) {
        String myId = (String) session.getAttribute("userId");
        UserProfileResponse myTeam = teamRankingService.getMyTeamUser(myId);

        return ResponseEntity.ok(myTeam);
    }
}
