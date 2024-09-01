package com.tmp.xmash.web;

import com.tmp.xmash.dto.RankingResponse;
import com.tmp.xmash.service.RankingService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponse>> getRanking() {
        return ResponseEntity.ok(rankingService.getRanking());
    }

}
