package com.tmp.xmash.domain;

import static com.tmp.xmash.common.AppConstants.RANDOM_GENERATOR;

import com.tmp.xmash.db.entity.SingleNormalMatchHistory;
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
        int randomDigit = RANDOM_GENERATOR.nextInt(1, 10);  // 1에서 9까지의 숫자 생성

        return  10 + randomDigit;
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

    public boolean isHomeWinner() {
        return homeScore > awayScore;
    }


    public SingleRankMatchHistory resolveMatchWinner2()  {
        if (homeScore == awayScore) {
            throw new IllegalArgumentException("무승부 없음");
        }

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
