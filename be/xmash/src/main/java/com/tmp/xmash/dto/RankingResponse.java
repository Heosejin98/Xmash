package com.tmp.xmash.dto;

import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.type.Tier;

public record RankingResponse(String userId, String userName, int lp, int rank, Tier tier) {

    public static RankingResponse from(UserRanking userRanking) {
        return new RankingResponse(null,
                null,
                userRanking.getLp(),
                userRanking.getRanking(),
                userRanking.getTier());
    }

}
