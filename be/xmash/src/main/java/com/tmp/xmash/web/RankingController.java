package com.tmp.xmash.web;

import com.tmp.xmash.dto.response.RankingResponse;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.web.editor.MatchTypeEditor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "rank", description = "랭킹 전적 관련 api")
public class RankingController {

    private final RankingService rankingService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(MatchType.class, new MatchTypeEditor());
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponse>> rankingGet(
            @Parameter(name = "matchType", description = "매치타입(단식, 복식)을 지정합니다.", required = true) @RequestParam MatchType matchType
    ) throws BadRequestException {
        return ResponseEntity.ok(rankingService.getRanking(matchType));
    }

}
