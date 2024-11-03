package com.tmp.xmash.service.game;

import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.exption.GamePostException;

public interface GamePostAble {

    boolean matchDone(GameResultRequest gameResultRequest);

    default void checkScore(GameResultRequest gameResultRequest) {
        if (gameResultRequest.homeScore() == gameResultRequest.awayScore()) {
            throw new GamePostException("무승부는 등록 불가능합니다.");
        }

        int winnerScore = Math.max(gameResultRequest.homeScore(), gameResultRequest.awayScore());
        if (winnerScore < 11) {
            throw new GamePostException("11점 이상 게임만 등록 가능합니다.");
        }
    }

}
