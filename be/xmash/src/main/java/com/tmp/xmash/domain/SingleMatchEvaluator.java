package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleRankMatchHistory;

import java.util.List;
import java.util.Set;

public class SingleMatchEvaluator extends MatchEvaluator {
    private final String homeId;
    private final String awayId;

    public SingleMatchEvaluator(String homeId, String awayId, int homeScore, int awayScore) {
        super(homeScore, awayScore, getResultLp(homeScore, awayScore));
        this.homeId = homeId;
        this.awayId = awayId;
    }

    public SingleRankMatchHistory resolveMatchWinner()  {
        if (isHomeWinner()) {
            return SingleRankMatchHistory.builder()
                    .winnerId(homeId)
                    .winnerScore(homeScore)
                    .loserId(awayId)
                    .loserScore(awayScore)
                    .lp(resultLp)
                    .build();
        }

        return SingleRankMatchHistory.builder()
                .winnerId(awayId)
                .winnerScore(awayScore)
                .loserId(homeId)
                .loserScore(homeScore)
                .lp(resultLp)
                .build();
    }

    @Override
    public List<String> getUserIds() {
        return List.of(homeId, awayId);
    }

    @Override
    public List<AppUser> getAwayUser(List<AppUser> matchUsers) {
        Set<String> awayIds = Set.of(awayId);

        return matchUsers.stream()
                .filter(matchUser -> awayIds.contains(matchUser.getUserId()))
                .toList();
    }

    @Override
    public List<AppUser> getHomeUser(List<AppUser> matchUsers){
        Set<String> homeIds = Set.of(homeId);

        return matchUsers.stream()
                .filter(matchUser -> homeIds.contains(matchUser.getUserId()))
                .toList();

    }
}
