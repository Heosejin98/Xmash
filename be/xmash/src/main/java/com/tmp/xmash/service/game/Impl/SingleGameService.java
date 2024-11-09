package com.tmp.xmash.service.game.Impl;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleRankMatchHistory;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.SingleRankMatchHistoryRepo;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.domain.SingleMatchEvaluator;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.service.UserService;
import com.tmp.xmash.service.game.GamePostAble;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.type.MatchType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class SingleGameService implements GameService, GamePostAble {

    private static final MatchType matchType = MatchType.SINGLE;

    private final SingleRankMatchHistoryRepo singleMatchHistoryRepo;
    private final UserRepository userRepository;
    private final RankingService rankingService;
    private final UserService userService;

    @Transactional
    @Override
    public boolean matchDone(MatchEvaluator evaluator) {
        SingleMatchEvaluator matchEvaluator = (SingleMatchEvaluator) evaluator;
        matchEvaluator.checkScore();

        List<AppUser> matchUsers = userService.findByUserIdIn(matchEvaluator.getUserIds());
        AppUser homeUser = matchEvaluator.getHomeUser(matchUsers).stream()
                .findFirst()
                .orElseThrow();
        AppUser awayUser = matchEvaluator.getAwayUser(matchUsers).stream()
                .findFirst()
                .orElseThrow();

        singleMatchHistoryRepo.save(matchEvaluator.resolveMatchWinner());

        UserRanking homeSeasonRanking =  homeUser.getCurrentUserRanking();
        UserRanking awaySeasonRanking =  awayUser.getCurrentUserRanking();

        if (evaluator.isHomeWinner()) {
            rankingService.updateRanking(homeSeasonRanking, awaySeasonRanking, evaluator.getResultLp());

            return true;
        }

        rankingService.updateRanking(awaySeasonRanking, homeSeasonRanking, evaluator.getResultLp());
        return false;
    }


    @Transactional
    @Override
    public List<GameResultResponse> getMatchHistory() {
        Map<String, AppUser> usersByUserId = userRepository.findAll().stream()
                .collect(toMap(AppUser::getUserId, identity()));
        List<SingleRankMatchHistory> singleMatchHistories = singleMatchHistoryRepo.findTop1000ByOrderByMatchTimeDesc();

        return singleMatchHistories.stream()
                .map(a -> GameResultResponse.createSingleGame(a, usersByUserId, matchType))
                .toList();
    }


}
