package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.dto.response.TournamentGameResponse;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.service.game.GameServiceFactory;
import com.tmp.xmash.service.game.TournamentGameService;
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
import lombok.AllArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Game", description = "Game 전적 관련 api")
public class GameController {

    private final GameServiceFactory gameServiceFactory;
    private final TournamentGameService tournamentGameService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(GameType.class, new GameTypeEditor());
        binder.registerCustomEditor(MatchType.class, new MatchTypeEditor());
    }

    @PostMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<Boolean> doneGameResult(
            HttpSession session,
        @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("gameType") GameType gameType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) throws AuthenticationException {

        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            throw new AuthenticationException("로그인 후 게임 등록 가능");
        }

        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.matchDone(gameResultRequest));
    }


    @PostMapping("/game/tournament")
    public ResponseEntity<Boolean> doneTournamentGameResult(
            HttpSession session
    )  {
        String userId = (String) session.getAttribute("userId");

        tournamentGameService.registration(userId);

        return ResponseEntity.ok(true);
    }


    @GetMapping("/game/{gameType}/{matchType}")
    public ResponseEntity<List<GameResultResponse>> getGameResult(
            @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable MatchType matchType,
            @Parameter(name = "gameType", description = "게임타입(랭크, 노말)을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable GameType gameType
    ) {
        GameService gameService = gameServiceFactory.getGameService(matchType, gameType);

        return ResponseEntity.ok(gameService.getMatchHistory());
    }

    @GetMapping("/game/tournament")
    @Operation(summary = "토너먼트 경기 조회", description = "시즌 별 토너먼트 경기 결과 or 진행 상태 조회")
    public ResponseEntity<List<TournamentGameResponse>> getTournamentGame(
            @RequestParam int season
    ) {
        return ResponseEntity.ok(tournamentGameService.getTournamentInfo(season));
    }



    @GetMapping("/match-type")
    @Operation(summary = "모든 경기 타입 조회", description = "모든 경기 타입을 조회 (ALL, SINGLE, DOUBLE)")
    public ResponseEntity<List<String>> getGameResult() {
        return ResponseEntity.ok(MatchType.getMatchTypes());
    }



}
