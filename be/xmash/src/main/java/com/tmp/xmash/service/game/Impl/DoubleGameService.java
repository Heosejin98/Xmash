package com.tmp.xmash.service.game.Impl;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.DoubleRankMatchRepo;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.domain.DoubleMatchEvaluator;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.service.game.GamePostAble;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.type.MatchType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class DoubleGameService implements GameService, GamePostAble {

    private final static MatchType matchType = MatchType.DOUBLE;

    private final DoubleRankMatchRepo doubleRankMatchRepo;
    private final UserRepository userRepository;
    private final RankingService rankingService;


    @Transactional
    @Override
    public boolean matchDone(MatchEvaluator matchEvaluator)  {
        DoubleMatchEvaluator matchEvaluator2 = (DoubleMatchEvaluator) matchEvaluator;
        matchEvaluator.checkScore();

        List<AppUser> matchUsers = userRepository.findByUserIdIn(matchEvaluator2.getUserIds());
        List<AppUser> homeUsers = matchEvaluator2.getHomeUser(matchUsers);
        List<AppUser> awayUser = matchEvaluator2.getAwayUser(matchUsers);
        List<UserRanking> homeSeasonRanking = homeUsers.stream()
                .map(AppUser::getCurrentUserRanking)
                .toList();
        List<UserRanking> awaySeasonRanking =  awayUser.stream()
                .map(AppUser::getCurrentUserRanking)
                .toList();

        doubleRankMatchRepo.save(matchEvaluator2.resolveRankMatchWinner());

        if (matchEvaluator.isHomeWinner()) {
            rankingService.updateRanking(homeSeasonRanking, awaySeasonRanking, matchEvaluator.getResultLp());

            return true;
        }

        rankingService.updateRanking(awaySeasonRanking, homeSeasonRanking, matchEvaluator.getResultLp());
        return false;
    }

    @Override
    public List<GameResultResponse> getMatchHistory() {
        Map<String, AppUser> usersByUserId = userRepository.findAll().stream()
                .collect(toMap(AppUser::getUserId, identity()));
        List<DoubleRankMatchHistory> singleMatchHistories = doubleRankMatchRepo.findTop1000ByOrderByMatchTimeDesc();

        return singleMatchHistories.stream()
                .map(matchHistory -> GameResultResponse.createDoubleRankGame(matchHistory, usersByUserId, matchType))
                .toList();
    }

}
