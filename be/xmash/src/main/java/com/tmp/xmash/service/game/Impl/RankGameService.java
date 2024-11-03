package com.tmp.xmash.service.game.Impl;

import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankGameService implements GameService {

    private final GameService doubleGameService;
    private final GameService singleGameService;

    @Override
    public List<GameResultResponse> getMatchHistory() {
        List<GameResultResponse> singleRankGameResult = doubleGameService.getMatchHistory();
        List<GameResultResponse> doubleRankGameResult = singleGameService.getMatchHistory();

        List<GameResultResponse> mergedResult = new ArrayList<>(singleRankGameResult);
        mergedResult.addAll(doubleRankGameResult);

        return mergedResult.stream()
                .sorted()
                .toList();
    }
}
