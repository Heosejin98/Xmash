package com.tmp.xmash.service.game;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleRankMatchHistory;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.SingleRankMatchHistoryRepo;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.type.MatchType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class SingleRankGameService implements GameService, GamePostAble {

    private final SingleRankMatchHistoryRepo singleMatchHistoryRepo;

    private final UserRepository userRepository;

    private final RankingService rankingService;

    private static final MatchType matchType = MatchType.SINGLE;

    @Transactional
    @Override
    public boolean matchDone(GameResultRequest gameResultRequest) {
        checkScore(gameResultRequest);

        Set<AppUser> matchUsers = userRepository.findByUserIdIn(Set.of(
                gameResultRequest.homeTeam().getFirst(),
                gameResultRequest.awayTeam().getFirst()));
        AppUser homeUser = matchUsers.stream()
                .filter(a -> a.getUserId().equals(gameResultRequest.homeTeam().getFirst()))
                .findFirst()
                .orElseThrow();
        AppUser awayUser = matchUsers.stream()
                .filter(a -> a.getUserId().equals(gameResultRequest.awayTeam().getFirst()))
                .findFirst()
                .orElseThrow();

        MatchEvaluator matchEvaluator = new MatchEvaluator(gameResultRequest);
        singleMatchHistoryRepo.save(matchEvaluator.resolveMatchWinner2());

        UserRanking homeSeasonRanking =  homeUser.getCurrentUserRanking();
        UserRanking awaySeasonRanking =  awayUser.getCurrentUserRanking();

        if (matchEvaluator.isHomeWinner()) {
            rankingService.updateRanking(homeSeasonRanking, awaySeasonRanking, matchEvaluator.getResultLp());

            return true;
        }

        rankingService.updateRanking(awaySeasonRanking, homeSeasonRanking, matchEvaluator.getResultLp());
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
