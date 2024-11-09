package com.tmp.xmash.service.game;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.dto.request.GameModifyRequest;
import com.tmp.xmash.domain.RequestUserRanking;

import java.util.List;

public interface GamePostAble {

    boolean matchDone(MatchEvaluator gameResultRequest);

    void modifyMatchHistory(GameModifyRequest matchEvaluator, long matchId);

    default RequestUserRanking getRequestUserRanking(MatchEvaluator doubleMatchEvaluator, List<AppUser> matchUsers) {
        List<AppUser> homeUsers = doubleMatchEvaluator.getHomeUser(matchUsers);
        List<AppUser> awayUser = doubleMatchEvaluator.getAwayUser(matchUsers);
        List<UserRanking> homeSeasonRanking = homeUsers.stream()
                .map(AppUser::getCurrentUserRanking)
                .toList();
        List<UserRanking> awaySeasonRanking =  awayUser.stream()
                .map(AppUser::getCurrentUserRanking)
                .toList();
        return new RequestUserRanking(homeSeasonRanking, awaySeasonRanking);
    }
}
