package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.DoubleNormalMatchHistory;
import com.tmp.xmash.dto.request.GameResultRequest;
import java.util.List;

public class DoubleMatchEvaluator {

    private final List<String> homeTeamIds;

    private final List<String> awayTeamIds;

    private final int homeScore;

    private final int awayScore;

    public DoubleMatchEvaluator(GameResultRequest gameResultRequest) {
        this.homeTeamIds = gameResultRequest.homeTeam();
        this.awayTeamIds = gameResultRequest.awayTeam();
        this.homeScore = gameResultRequest.homeScore();
        this.awayScore = gameResultRequest.awayScore();
    }

    public DoubleNormalMatchHistory resolveMatchWinner() {
        if (homeScore == awayScore) {
            throw new IllegalArgumentException("무승부 없음");
        }

        if (homeScore > awayScore) {
            return DoubleNormalMatchHistory.builder()
                    .winner1Id(homeTeamIds.get(0))
                    .winner2Id(homeTeamIds.get(1))
                    .winnerScore(homeScore)
                    .loser1Id(awayTeamIds.get(0))
                    .loser2Id(awayTeamIds.get(1))
                    .loserScore(awayScore)
                    .build();
        }

        return DoubleNormalMatchHistory.builder()
                .winner1Id(awayTeamIds.get(0))
                .winner2Id(awayTeamIds.get(1))
                .winnerScore(awayScore)
                .loser1Id(homeTeamIds.get(0))
                .loser2Id(homeTeamIds.get(1))
                .loserScore(homeScore)
                .build();
    }
}
