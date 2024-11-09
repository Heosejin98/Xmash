package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;

import java.util.List;

public class DoubleMatchEvaluator extends MatchEvaluator {

    public DoubleMatchEvaluator(List<String> homeTeamIds, List<String> awayUserIds, int homeScore, int awayScore) {
        super(homeScore,
                awayScore,
                crateResultLp(homeScore, awayScore),
                new Team(homeTeamIds.getFirst(), homeTeamIds.getLast()),
                new Team(awayUserIds.getFirst(), awayUserIds.getLast()));
    }

    @Override
    public List<String> getUserIds() {
        return List.of(homeTeam.userId1(), homeTeam.userId2(),
                awayTeam.userId1(), awayTeam.userId2());
    }

    @Override
    public List<AppUser> getHomeUser(List<AppUser> matchUsers) {
        return matchUsers.stream()
                .filter(user -> homeTeam.userId1().equals(user.getUserId()) ||
                        homeTeam.userId2().equals(user.getUserId()))
                .toList();
    }

    @Override
    public List<AppUser> getAwayUser(List<AppUser> matchUsers) {
        return matchUsers.stream()
                .filter(user -> awayTeam.userId1().equals(user.getUserId()) ||
                        awayTeam.userId2().equals(user.getUserId()))
                .toList();
    }

    public DoubleRankMatchHistory resolveRankMatchWinner()  {
        if (homeScore > awayScore) {
            return DoubleRankMatchHistory.builder()
                    .winner1Id(homeTeam.userId1())
                    .winner2Id(homeTeam.userId2())
                    .winnerScore(homeScore)
                    .loser1Id(awayTeam.userId1())
                    .loser2Id(awayTeam.userId2())
                    .loserScore(awayScore)
                    .lp(resultLp)
                    .build();
        }

        return DoubleRankMatchHistory.builder()
                .winner1Id(awayTeam.userId1())
                .winner2Id(awayTeam.userId2())
                .winnerScore(awayScore)
                .loser1Id(homeTeam.userId1())
                .loser2Id(homeTeam.userId2())
                .loserScore(homeScore)
                .lp(resultLp)
                .build();
    }
}
