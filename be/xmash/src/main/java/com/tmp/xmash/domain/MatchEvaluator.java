package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.SingleNormalMatchHistory;
import com.tmp.xmash.dto.request.GameResultRequest;

public class MatchEvaluator {

    private final String homeId;

    private final String awayId;

    private final int homeScore;

    private final int awayScore;

    public MatchEvaluator(GameResultRequest gameResultRequest) {
        this.homeId = gameResultRequest.homeTeam().getFirst();
        this.awayId = gameResultRequest.awayTeam().getFirst();
        this.homeScore = gameResultRequest.homeScore();
        this.awayScore = gameResultRequest.awayScore();
    }

    public SingleNormalMatchHistory resolveMatchWinner()  {
        if (homeScore == awayScore) {
            throw new IllegalArgumentException("무승부 없음");
        }

        if (homeScore > awayScore) {
            return SingleNormalMatchHistory.builder()
                    .winnerId(homeId)
                    .winnerScore(homeScore)
                    .loserId(awayId)
                    .loserScore(awayScore)
                    .build();
        }

        return SingleNormalMatchHistory.builder()
                .winnerId(awayId)
                .winnerScore(awayScore)
                .loserId(homeId)
                .loserScore(homeScore)
                .build();
    }

}
