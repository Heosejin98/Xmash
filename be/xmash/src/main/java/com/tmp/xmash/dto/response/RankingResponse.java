package com.tmp.xmash.dto.response;

import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.type.Tier;

public record RankingResponse(String userId, String userName, int lp, int rank, Tier tier) {

    public static RankingResponse createSingleRanking(UserRanking userRanking, int rank) {
        return new RankingResponse(userRanking.getAppUser().getUserId(),
                userRanking.getAppUser().getName(),
                userRanking.getLp(),
                rank,
                userRanking.getTier());
    }

    public static RankingResponse createDoubleRanking(UserRanking userRanking, int rank) {
        return new RankingResponse(userRanking.getAppUser().getUserId(),
                userRanking.getAppUser().getName(),
                userRanking.getTeamLp(),
                rank,
                userRanking.getTeamTier());
    }

}
