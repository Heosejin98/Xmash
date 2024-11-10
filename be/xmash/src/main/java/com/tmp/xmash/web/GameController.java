package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.GameModifyRequest;
import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.dto.response.TournamentGameResponse;
import com.tmp.xmash.exption.AuthenticationException;
import com.tmp.xmash.service.game.GamePostAble;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.service.game.GameServiceFactory;
import com.tmp.xmash.service.game.TournamentGameService;
import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.web.editor.GameTypeEditor;
import com.tmp.xmash.web.editor.MatchTypeEditor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    private final GameServiceFactory gameServiceFactory;
    private final TournamentGameService tournamentGameService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(GameType.class, new GameTypeEditor());
        binder.registerCustomEditor(MatchType.class, new MatchTypeEditor());
    }

    @PostMapping("/games")
    public ResponseEntity<Boolean> doneGameResult(
            HttpSession session,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            throw new AuthenticationException("로그인 후 게임 등록 가능");
        }

        GamePostAble gameService = gameServiceFactory.getGamePostAble(gameResultRequest.matchType());

        return ResponseEntity.ok(gameService.matchDone(gameResultRequest.toMatchEvaluator()));
    }

    @PatchMapping("/games/{matchId}")
    public ResponseEntity<Void> modifyGameResult(
            HttpSession session,
            @Parameter(name = "matchId", description = "match_history_id") long matchId,
            @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameModifyRequest gameModifyRequest
    ) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            throw new AuthenticationException("로그인 후 게임 수정 가능");
        }

        GamePostAble gameService = gameServiceFactory.getGamePostAble(gameModifyRequest.matchType());
        gameService.modifyMatchHistory(gameModifyRequest, matchId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameResultResponse>> getGameResult(
            @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true) @RequestParam MatchType matchType
    ) {
        GameService gameService = gameServiceFactory.getGameService(matchType);

        return ResponseEntity.ok(gameService.getMatchHistory());
    }


    @PostMapping("/games/tournament")
    @Operation(summary = "토너먼트 경기 신청", description = "오픈된 시즌 토너먼트 신청")
    public ResponseEntity<Boolean> doneTournamentGameResult(
            HttpSession session
    )  {
        String userId = (String) session.getAttribute("userId");

        tournamentGameService.registration(userId);

        return ResponseEntity.ok(true);
    }




    @GetMapping("/games/tournament")
    @Operation(summary = "토너먼트 경기 조회", description = "시즌 별 토너먼트 경기 결과 or 진행 상태 조회")
    public ResponseEntity<List<TournamentGameResponse>> getTournamentGame(
            @RequestParam int season
    ) {
        return ResponseEntity.ok(tournamentGameService.getTournamentInfo(season));
    }
}
