package com.tmp.xmash.service.game;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;
import com.tmp.xmash.db.repositroy.DoubleRankMatchRepo;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.domain.DoubleMatchEvaluator;
import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.type.MatchType;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoubleRankGameService implements GameService {

    private final DoubleRankMatchRepo doubleRankMatchRepo;
    private final UserRepository userRepository;
    private final static MatchType matchType = MatchType.DOUBLE;

    @Transactional
    @Override
    public boolean matchDone(GameResultRequest gameResultRequest) {
        DoubleMatchEvaluator matchEvaluator = new DoubleMatchEvaluator(gameResultRequest);

        DoubleRankMatchHistory singleMatchHistory = matchEvaluator.resolveMatchWinner2();
        doubleRankMatchRepo.save(singleMatchHistory);

        return singleMatchHistory.getWinner1Id().equals(gameResultRequest.homeTeam().getFirst())
                && singleMatchHistory.getWinner2Id().equals(gameResultRequest.homeTeam().get(1));
    }

    @Override
    public List<GameResultResponse> getMatchHistory() {
        Map<String, AppUser> usersByUserId = userRepository.findAll().stream()
                .collect(toMap(AppUser::getUserId, identity()));
        List<DoubleRankMatchHistory> singleMatchHistories = doubleRankMatchRepo.findTop1000ByOrderByMatchTimeDesc();

        return singleMatchHistories.stream()
                .map(a -> GameResultResponse.createDoubleRankGame(a, usersByUserId, matchType))
                .toList();
    }

}
