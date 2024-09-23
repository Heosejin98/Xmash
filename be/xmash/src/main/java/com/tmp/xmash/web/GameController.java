package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.SingleNormalGameService;
import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.web.editor.GameTypeEditor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Game", description = "Game 전적 관련 api")
public class GameController {

    private final SingleNormalGameService gameResultService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(GameType.class, new GameTypeEditor());
    }

    @PostMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<Boolean> updateSingleGameResult(
        HttpSession session,
        @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("gameType") GameType gameType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) {
        String homeId = (String) session.getAttribute("userId");

        return ResponseEntity.ok(gameResultService.matchDone(gameResultRequest, homeId));
    }


    @GetMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<List<GameResultResponse>> getSingleGameResult(
            @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
            @Parameter(name = "gameType", description = "게임타입(랭크, 노말)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable GameType gameType
    ) {
        //TODO : matchType gameType에 따라 다르게 동작하도록 구현
        return ResponseEntity.ok(gameResultService.getMatchHistory());
    }


}
