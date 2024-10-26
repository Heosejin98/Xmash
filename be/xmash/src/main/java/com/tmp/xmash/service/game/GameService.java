package com.tmp.xmash.service.game;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import java.util.List;

public interface GameService {

    boolean matchDone(GameResultRequest gameResultRequest);

    List<GameResultResponse> getMatchHistory();

}