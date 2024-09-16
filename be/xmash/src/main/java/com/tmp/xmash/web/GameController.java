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
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable GameType gameType,
        @Parameter(name = "matchType", description = "경기 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) {
        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.matchDone(gameResultRequest));
    }


    @GetMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<List<GameResultResponse>> getSingleGameResult(
            @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable GameType gameType,
            @Parameter(name = "matchType", description = "경기 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType
    ) {
        //TODO : matchType gameType에 따라 다르게 동작하도록 구현
        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.getMatchHistory());
    }

}
