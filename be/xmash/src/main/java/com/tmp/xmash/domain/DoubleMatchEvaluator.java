package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;

import java.util.List;
import java.util.stream.Stream;

public class DoubleMatchEvaluator extends MatchEvaluator {

    private final List<String> homeUserIds;

    private final List<String> awayUserIds;

    public DoubleMatchEvaluator(List<String> homeTeamIds, List<String> awayUserIds, int homeScore, int awayScore) {
        super(homeScore, awayScore, getResultLp(homeScore, awayScore));
        this.homeUserIds = homeTeamIds;
        this.awayUserIds = awayUserIds;
    }

    @Override
    public List<String> getUserIds() {
        return Stream.concat(homeUserIds.stream(), awayUserIds.stream())
                .toList();
    }

    @Override
    public List<AppUser> getHomeUser(List<AppUser> matchUsers) {
        return matchUsers.stream()
                .filter(a -> homeUserIds.contains(a.getUserId()))
                .toList();
    }

    @Override
    public List<AppUser> getAwayUser(List<AppUser> matchUsers) {
        return matchUsers.stream()
                .filter(a -> awayUserIds.contains(a.getUserId()))
                .toList();
    }

    public DoubleRankMatchHistory resolveRankMatchWinner()  {
        if (homeScore > awayScore) {
            return DoubleRankMatchHistory.builder()
                    .winner1Id(homeUserIds.get(0))
                    .winner2Id(homeUserIds.get(1))
                    .winnerScore(homeScore)
                    .loser1Id(awayUserIds.get(0))
                    .loser2Id(awayUserIds.get(1))
                    .loserScore(awayScore)
                    .build();
        }

        return DoubleRankMatchHistory.builder()
                .winner1Id(awayUserIds.get(0))
                .winner2Id(awayUserIds.get(1))
                .winnerScore(awayScore)
                .loser1Id(homeUserIds.get(0))
                .loser2Id(homeUserIds.get(1))
                .loserScore(homeScore)
                .build();
    }
}
