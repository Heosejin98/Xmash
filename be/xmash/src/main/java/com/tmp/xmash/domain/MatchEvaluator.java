package com.tmp.xmash.domain;

import static com.tmp.xmash.common.AppConstants.DEFAULT_WINNER_LP;
import static com.tmp.xmash.common.AppConstants.RANDOM_GENERATOR;

import com.tmp.xmash.db.entity.SingleRankMatchHistory;
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

    public int getResultLp() {
        int winnerScore = isHomeWinner() ? homeScore : awayScore;

        if (winnerScore <= 11) {
            return DEFAULT_WINNER_LP;
        }

        if (winnerScore <= 20) {
            return DEFAULT_WINNER_LP + RANDOM_GENERATOR.nextInt(5);
        }

        return DEFAULT_WINNER_LP + (RANDOM_GENERATOR.nextInt(5) * 2);
    }

    public boolean isHomeWinner() {
        return homeScore > awayScore;
    }

    public SingleRankMatchHistory resolveMatchWinner2()  {
        if (isHomeWinner()) {
            return SingleRankMatchHistory.builder()
                    .winnerId(homeId)
                    .winnerScore(homeScore)
                    .loserId(awayId)
                    .loserScore(awayScore)
                    .build();
        }

        return SingleRankMatchHistory.builder()
                .winnerId(awayId)
                .winnerScore(awayScore)
                .loserId(homeId)
                .loserScore(homeScore)
                .build();
    }

}
