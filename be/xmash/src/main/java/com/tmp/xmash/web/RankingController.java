package com.tmp.xmash.web;

import com.tmp.xmash.dto.RankingResponse;
import com.tmp.xmash.openapi.rest.RankingApi;
import com.tmp.xmash.service.RankingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RankingController implements RankingApi {

    private final RankingService rankingService;

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponse>> rankingGet() {
        return ResponseEntity.ok(rankingService.getRanking());
    }

}
