package com.tmp.xmash.service.game;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleNormalMatchHistory;
import com.tmp.xmash.db.repositroy.SingleMatchHistoryRepo;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
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
public class SingleNormalGameService implements GameService, GamePostAble {

    private final SingleMatchHistoryRepo singleMatchHistoryRepo;

    private final UserRepository userRepository;

    private static final MatchType matchType = MatchType.SINGLE;


    @Transactional
    @Override
    public boolean matchDone(GameResultRequest gameResultRequest) {
        checkScore(gameResultRequest);

        MatchEvaluator matchEvaluator = new MatchEvaluator(gameResultRequest);

        SingleNormalMatchHistory singleNormalMatchHistory = matchEvaluator.resolveMatchWinner();
        singleMatchHistoryRepo.save(singleNormalMatchHistory);

        return matchEvaluator.isHomeWinner();
    }


    @Transactional
    @Override
    public List<GameResultResponse> getMatchHistory() {
        Map<String, AppUser> usersByUserId = userRepository.findAll().stream()
                .collect(toMap(AppUser::getUserId, identity()));
        List<SingleNormalMatchHistory> singleMatchHistories = singleMatchHistoryRepo.findTop1000ByOrderByMatchTimeDesc();

        return singleMatchHistories.stream()
                .map(a -> GameResultResponse.createSingleGame(a, usersByUserId, matchType))
                .toList();
    }


}
