package com.tmp.xmash.dto.response;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleMatchHistory;
import com.tmp.xmash.type.MatchType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record GameResultResponse(
        List<PlayerResponse> winTeam,
        List<PlayerResponse> loseTeam,
        int winnerScore,
        int loserScore,
        LocalDateTime matchTime,
        MatchType matchType,
        Integer point
) {

    public static GameResultResponse createSingleGame(SingleMatchHistory matchResult, Map<String, AppUser> userByUserId) {
        String winnerId = matchResult.getWinnerId();
        String winnerName = userByUserId.get(winnerId).getName();
        String loserId = matchResult.getLoserId();
        String loserName = userByUserId.get(loserId).getName();

        List<PlayerResponse> winTeam = List.of(new PlayerResponse(winnerId, winnerName, null));
        List<PlayerResponse> loserTeam = List.of(new PlayerResponse(loserId, loserName, null));

        return new GameResultResponse(
            winTeam,
            loserTeam,
            matchResult.getWinnerScore(),
            matchResult.getLoserScore(),
            matchResult.getMatchTime(),
            matchResult.getMatchType(),
        null
        );
    }

}
