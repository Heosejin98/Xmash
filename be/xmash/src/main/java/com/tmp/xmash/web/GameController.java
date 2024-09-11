package com.tmp.xmash.web;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.SingleNormalGameService;
import com.tmp.xmash.type.GameType;
import com.tmp.xmash.web.editor.GameTypeEditor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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

    private final SingleNormalGameService gameResultService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(GameType.class, new GameTypeEditor());
    }

    @PostMapping("/game/{gameType}")
    public ResponseEntity<Boolean> updateSingleGameResult(
        HttpSession session,
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("gameType") GameType gameType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameResultRequest gameResultRequest
    ) {
        String homeId = (String) session.getAttribute("userId");

        return ResponseEntity.ok(gameResultService.matchDone(gameResultRequest, homeId));
    }


    @GetMapping("/game/{gameType}")
    public ResponseEntity<List<GameResultResponse>> getSingleGameResult(
            @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable GameType gameType
    ) {
        //TODO : matchType gameType에 따라 다르게 동작하도록 구현
        return ResponseEntity.ok(gameResultService.getMatchHistory());
    }

}
