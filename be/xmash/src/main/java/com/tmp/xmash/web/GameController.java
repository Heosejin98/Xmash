package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.service.game.GameServiceFactory;
import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.web.editor.GameTypeEditor;
import com.tmp.xmash.web.editor.MatchTypeEditor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
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
            HttpSession session,
        @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("gameType") GameType gameType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) throws BadRequestException, AuthenticationException {

        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            throw new AuthenticationException("로그인 후 게임 등록 가능");
        }

        if (MatchType.ALL == matchType) {
            throw new BadRequestException("ALL 타입 등록 불가능");
        }

        if (!gameResultRequest.homeTeam().contains(userId)) { //home팀에 로그인한 계정이 없는 경우
            throw new BadRequestException("home User에 내 계정을 넣으세요");
        }

        if (Objects.equals(gameResultRequest.homeScore(), gameResultRequest.awayScore())) {
            throw new BadRequestException("무승부는 등록 불가능합니다.");
        }

        int winnerScore = Math.max(gameResultRequest.homeScore(), gameResultRequest.awayScore());
        if (winnerScore < 11) {
            throw new BadRequestException("11점 이상 게임만 등록 가능합니다.");
        }

        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.matchDone(gameResultRequest));
    }


    @GetMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<List<GameResultResponse>> getGameResult(
            @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
            @Parameter(name = "gameType", description = "게임타입(랭크, 노말)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable GameType gameType
    ) {
        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.getMatchHistory());
    }


    @GetMapping("/match-type")
    @Operation(summary = "모든 경기 타입 조회", description = "모든 경기 타입을 조회 (ALL, SINGLE, DOUBLE)")
    public ResponseEntity<List<String>> getGameResult() {
        return ResponseEntity.ok(MatchType.getMatchTypes());
    }



}
