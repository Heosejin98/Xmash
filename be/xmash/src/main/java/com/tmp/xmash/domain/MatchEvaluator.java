package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.SingleMatchHistory;
import com.tmp.xmash.dto.GameResultRequest;
import com.tmp.xmash.type.MatchType;

public class MatchEvaluator {

    private final String homeId;

    private final String awayId;

    private final int homeScore;

    private final int awayScore;

    private final MatchType matchType;

    public MatchEvaluator(String homeId, MatchType matchType, GameResultRequest gameResultRequest) {
        this.homeId = homeId;
        this.awayId = gameResultRequest.awayPlayerId();
        this.homeScore = gameResultRequest.homeScore();
        this.awayScore = gameResultRequest.awayScore();
        this.matchType = matchType;
    }

    public SingleMatchHistory resolveMatchWinner()  {
        if (homeScore == awayScore) {
            throw new IllegalArgumentException("무승부 없음");
        }

        if (homeScore > awayScore) {
            return SingleMatchHistory.builder()
                    .winnerId(homeId)
                    .winnerScore(homeScore)
                    .loserId(awayId)
                    .loserScore(awayScore)
                    .matchType(matchType)
                    .build();
        }

        return SingleMatchHistory.builder()
                .winnerId(awayId)
                .winnerScore(awayScore)
                .loserId(homeId)
                .loserScore(homeScore)
                .matchType(matchType)
                .build();
    }

}
