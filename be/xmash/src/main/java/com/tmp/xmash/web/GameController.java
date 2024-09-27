package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.service.game.GameServiceFactory;
import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.web.editor.GameTypeEditor;
import com.tmp.xmash.web.editor.MatchTypeEditor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    private final GameServiceFactory gameServiceFactory;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(GameType.class, new GameTypeEditor());
        binder.registerCustomEditor(MatchType.class, new MatchTypeEditor());
    }

    @PostMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<Boolean> updateSingleGameResult(
        @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("gameType") GameType gameType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) {
        if (MatchType.ALL == matchType) {
            return ResponseEntity.status(400).body(false);
        }

        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.matchDone(gameResultRequest));
    }


    @GetMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<List<GameResultResponse>> getSingleGameResult(
            @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
            @Parameter(name = "gameType", description = "게임타입(랭크, 노말)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable GameType gameType
    ) {
        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.getMatchHistory());
    }

}
