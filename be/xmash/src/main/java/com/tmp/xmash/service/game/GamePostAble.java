package com.tmp.xmash.service.game;

import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.domain.SingleMatchEvaluator;

public interface GamePostAble {

    boolean matchDone(MatchEvaluator gameResultRequest);

//    void modifyMatchHistory(GameResultRequest gameResultRequest);

}
