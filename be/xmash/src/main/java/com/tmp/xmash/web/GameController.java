package com.tmp.xmash.web;

import com.tmp.xmash.openapi.domain.GameRequest;
import com.tmp.xmash.openapi.domain.GameType;


import com.tmp.xmash.openapi.rest.GameApi;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GameController implements GameApi {


    @PostMapping("/game/{gameType}")
    public ResponseEntity<Boolean> updateGameResult(
        @Parameter(name = "gameType", description = "게임 타입을 지정합니다.", required = true, in = ParameterIn.PATH) @PathVariable("gameType") GameType gameType,
        @Parameter(name = "GameRequest", description = "게임 결과 등록 요청 데이터", required = true) @Valid @RequestBody GameRequest gameRequestst
    ) {

        return ResponseEntity.ok(true);
    }

}
