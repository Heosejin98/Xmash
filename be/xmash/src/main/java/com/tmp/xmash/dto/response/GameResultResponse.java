package com.tmp.xmash.dto.response;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;
import com.tmp.xmash.db.entity.SingleRankMatchHistory;
import com.tmp.xmash.type.MatchType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record GameResultResponse(
        long idx,
        List<PlayerResponse> winTeam,
        List<PlayerResponse> loseTeam,
        int winnerScore,
        int loserScore,
        LocalDateTime matchTime,
        MatchType matchType,
        Integer point
) implements Comparable<GameResultResponse> {

    @Override
    public int compareTo(GameResultResponse other) {
        return other.matchTime.compareTo(this.matchTime);
    }

    public static GameResultResponse createSingleGame(SingleRankMatchHistory matchResult, Map<String, AppUser> userByUserId, MatchType matchType) {
        String winnerId = matchResult.getWinnerId();
        String winnerName = userByUserId.get(winnerId).getName();
        String loserId = matchResult.getLoserId();
        String loserName = userByUserId.get(loserId).getName();

        List<PlayerResponse> winTeam = List.of(new PlayerResponse(winnerId, winnerName, null));
        List<PlayerResponse> loserTeam = List.of(new PlayerResponse(loserId, loserName, null));

        return new GameResultResponse(
                matchResult.getId(),
                winTeam,
                loserTeam,
                matchResult.getWinnerScore(),
                matchResult.getLoserScore(),
                matchResult.getMatchTime(),
                matchType,
                null
        );
    }

    public static GameResultResponse createDoubleRankGame(DoubleRankMatchHistory matchResult, Map<String, AppUser> userByUserId, MatchType matchType) {
        String winnerId1 = matchResult.getWinner1Id();
        String winnerId2 = matchResult.getWinner2Id();
        String winnerName1 = userByUserId.get(winnerId1).getName();
        String winnerName2 = userByUserId.get(winnerId2).getName();
        String loserId1 = matchResult.getLoser1Id();
        String loserId2 = matchResult.getLoser2Id();
        String loserName1 = userByUserId.get(loserId1).getName();
        String loserName2 = userByUserId.get(loserId2).getName();

        List<PlayerResponse> winTeam = List.of(
                new PlayerResponse(winnerId1, winnerName1, null),
                new PlayerResponse(winnerId2, winnerName2, null)
        );
        List<PlayerResponse> loserTeam = List.of(
                new PlayerResponse(loserId1, loserName1, null),
                new PlayerResponse(loserId2, loserName2, null)
        );

        return new GameResultResponse(
                matchResult.getId(),
                winTeam,
                loserTeam,
                matchResult.getWinnerScore(),
                matchResult.getLoserScore(),
                matchResult.getMatchTime(),
                matchType,
                null
        );
    }

}
