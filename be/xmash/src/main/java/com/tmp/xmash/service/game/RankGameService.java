package com.tmp.xmash.service.game;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankGameService implements GameService {

    private final GameService doubleRankGameService;
    private final GameService singleRankGameService;


    @Override
    public boolean matchDone(GameResultRequest gameResultRequest) {
        throw new IllegalArgumentException("ALL MATCHES ARE DONE IN SINGLE OR DOUBLE MODE");
    }

    @Override
    public List<GameResultResponse> getMatchHistory() {
        List<GameResultResponse> singleRankGameResult = singleRankGameService.getMatchHistory();
        List<GameResultResponse> doubleRankGameResult = doubleRankGameService.getMatchHistory();

        List<GameResultResponse> mergedResult = new ArrayList<>(singleRankGameResult);
        mergedResult.addAll(doubleRankGameResult);

        return mergedResult.stream()
                .sorted()
                .toList();
    }
}
