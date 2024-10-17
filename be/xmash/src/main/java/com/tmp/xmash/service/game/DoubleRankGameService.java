package com.tmp.xmash.service.game;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.DoubleRankMatchRepo;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.domain.DoubleMatchEvaluator;
import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.type.MatchType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoubleRankGameService implements GameService {

    private final DoubleRankMatchRepo doubleRankMatchRepo;
    private final UserRepository userRepository;

    private final RankingService rankingService;

    private final static MatchType matchType = MatchType.DOUBLE;

    @Transactional
    @Override
    public boolean matchDone(GameResultRequest gameResultRequest)  {

        List<String> homeTeam = gameResultRequest.homeTeam();
        List<String> awayTeam = gameResultRequest.awayTeam();
        Set<String> userIds = Stream.concat(homeTeam.stream(), awayTeam.stream())
                .collect(Collectors.toSet());
        Set<AppUser> matchUsers = userRepository.findByUserIdIn(userIds);
        Set<AppUser> homeUsers = matchUsers.stream()
                .filter(a -> homeTeam.contains(a.getUserId()))
                .collect(Collectors.toSet());
        Set<AppUser> awayUser = matchUsers.stream()
                .filter(a -> awayTeam.contains(a.getUserId()))
                .collect(Collectors.toSet());
        Set<UserRanking> homeSeasonRanking = homeUsers.stream()
                .map(AppUser::getCurrentUserRanking)
                .collect(Collectors.toSet());
        Set<UserRanking> awaySeasonRanking =  awayUser.stream()
                .map(AppUser::getCurrentUserRanking)
                .collect(Collectors.toSet());

        DoubleMatchEvaluator matchEvaluator = new DoubleMatchEvaluator(gameResultRequest);
        DoubleRankMatchHistory singleMatchHistory = matchEvaluator.resolveRankMatchWinner();
        doubleRankMatchRepo.save(singleMatchHistory);

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
