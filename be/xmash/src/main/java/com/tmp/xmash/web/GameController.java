package com.tmp.xmash.web;

import com.tmp.xmash.dto.GameResultRequest;
import com.tmp.xmash.service.SingleNormalGameService;
import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Game", description = "Game 전적 관련 api")
public class GameController {

    private final SingleNormalGameService gameResultService;

    @PostMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<Boolean> updateSingleGameResult(
        HttpSession session,
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("gameType") GameType gameType,
        @Parameter(name = "matchType", description = "매치 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("matchType") MatchType matchType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) {
        String homeId = (String) session.getAttribute("userId");

        return ResponseEntity.ok(gameResultService.getMatchResult(gameResultRequest, homeId, matchType));
    }

}
