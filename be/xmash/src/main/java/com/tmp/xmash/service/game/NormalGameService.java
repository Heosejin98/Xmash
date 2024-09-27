package com.tmp.xmash.service.game;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NormalGameService implements GameService {

    private SingleNormalGameService singleNormalGameService;
    private DoubleNormalGameService doubleNormalGameService;

    @Override
    public boolean matchDone(GameResultRequest gameResultRequest) {
        throw new IllegalArgumentException("ALL MATCHES ARE DONE IN SINGLE OR DOUBLE MODE");
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameResultResponse> getMatchHistory() {
        List<GameResultResponse> singleGameResult = singleNormalGameService.getMatchHistory();
        List<GameResultResponse> doubleGameResult = doubleNormalGameService.getMatchHistory();

        List<GameResultResponse> mergedResult = new ArrayList<>(singleGameResult);
        mergedResult.addAll(doubleGameResult);

        return mergedResult.stream()
                .sorted()
                .toList();
    }
}
