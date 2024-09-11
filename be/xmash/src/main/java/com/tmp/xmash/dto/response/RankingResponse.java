package com.tmp.xmash.dto.response;

import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.type.Tier;

public record RankingResponse(String userId, String userName, int lp, int rank, Tier tier) {

    public static RankingResponse from(UserRanking userRanking) {
        return new RankingResponse(userRanking.getAppUser().getUserId(),
                userRanking.getAppUser().getName(),
                userRanking.getLp(),
                userRanking.getRanking(),
                userRanking.getTier());
    }

}
