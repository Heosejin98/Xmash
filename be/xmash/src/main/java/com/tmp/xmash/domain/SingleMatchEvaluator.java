package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleRankMatchHistory;

import java.util.List;

public class SingleMatchEvaluator extends MatchEvaluator {
    public SingleMatchEvaluator(String homeId, String awayId, int homeScore, int awayScore) {
        super(homeScore,
            awayScore,
            crateResultLp(homeScore, awayScore),
            new Team(homeId, null),
            new Team(awayId, null));
    }

    public SingleRankMatchHistory resolveMatchWinner()  {
        if (isHomeWinner()) {
            return SingleRankMatchHistory.builder()
                    .winnerId(homeTeam.userId1())
                    .winnerScore(homeScore)
                    .loserId(awayTeam.userId1())
                    .loserScore(awayScore)
                    .lp(resultLp)
                    .build();
        }

        return SingleRankMatchHistory.builder()
                .winnerId(homeTeam.userId1())
                .winnerScore(awayScore)
                .loserId(awayTeam.userId1())
                .loserScore(homeScore)
                .lp(resultLp)
                .build();
    }

    @Override
    public List<String> getUserIds() {
        return List.of(homeTeam.userId1(), awayTeam.userId1());
    }

    @Override
    public List<AppUser> getAwayUser(List<AppUser> matchUsers) {
        return matchUsers.stream()
                .filter(matchUser -> awayTeam.userId1().equals(matchUser.getUserId()))
                .toList();
    }

    @Override
    public List<AppUser> getHomeUser(List<AppUser> matchUsers){
        return matchUsers.stream()
                .filter(matchUser -> homeTeam.userId1().equals(matchUser.getUserId()))
                .toList();
    }
}
