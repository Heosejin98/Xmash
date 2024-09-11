package com.tmp.xmash.web;

import com.tmp.xmash.dto.response.RankingResponse;
import com.tmp.xmash.service.RankingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "rank", description = "랭킹 전적 관련 api")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponse>> rankingGet() {
        return ResponseEntity.ok(rankingService.getRanking());
    }

}
