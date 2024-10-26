package com.tmp.xmash.service.game;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import java.util.List;

public class TournamentGameService implements GameService {
    
    @Override
    public boolean matchDone(GameResultRequest gameResultRequest)  {
        return false;
    }

    @Override
    public List<GameResultResponse> getMatchHistory() {
        return List.of();
    }
}
