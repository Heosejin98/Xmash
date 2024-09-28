package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.PostTeamRequest;
import com.tmp.xmash.dto.response.PlayerResponse;
import com.tmp.xmash.dto.response.UserProfileResponse;
import com.tmp.xmash.service.TeamRankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rank")
@Tag(name = "Rank Game", description = "Rank Game 관련 api")
@RequiredArgsConstructor
public class RankController {

    private final TeamRankingService teamRankingService;

    @Operation(summary = "Double Rank Status 조회", description = "(Mock) 복식 게임 rank 게임 등록 해당 api 조회 하고 팀 없으면 팀등록 화면으로")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Double Rank 상태를 성공적으로 반환"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/team/status")
    public ResponseEntity<Boolean> getDoubleRank(
            HttpSession session
    ) {
        String homeId = (String) session.getAttribute("userId");

        return ResponseEntity.ok(true);
    }


    @Operation(summary = "팀에 속하지 않은 플레이어 조회", description = "(Mock) 복식 게임에서 팀에 속하지 않은 플레이어 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀에 속하지 않은 플레이어 목록을 성공적으로 반환"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/players/without-team")
    public ResponseEntity<List<PlayerResponse>> getPlayersWithoutTeam() {
        List<PlayerResponse> playerResponses  = List.of(
                new PlayerResponse("test", "허세진", "test"),
                new PlayerResponse("test1", "김진범", "test")
        );

        return ResponseEntity.ok(playerResponses);
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
    @GetMapping("/team/my/member")
    public ResponseEntity<PlayerResponse> getMyTeamId() {

        return ResponseEntity.ok(new PlayerResponse("test", "허세진", "test"));
    }
}
