package com.tmp.xmash.service.game;

import com.tmp.xmash.dto.response.GameResultResponse;
import java.util.List;

public interface GameService {

    List<GameResultResponse> getMatchHistory();

}