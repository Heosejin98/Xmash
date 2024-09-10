package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleMatchHistory;
import com.tmp.xmash.db.repositroy.SingleMatchHistoryRepo;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.dto.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.type.MatchType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Service
@AllArgsConstructor
public class SingleNormalGameService {

    private final SingleMatchHistoryRepo singleMatchHistoryRepo;

    private final UserRepository userRepository;

    private static final MatchType matchType = MatchType.SINGLE;


    @Transactional
    public boolean matchDone(GameResultRequest gameResultRequest, String homeId) {
        MatchEvaluator matchEvaluator = new MatchEvaluator(homeId, matchType, gameResultRequest);

        SingleMatchHistory singleMatchHistory = matchEvaluator.resolveMatchWinner();
        singleMatchHistoryRepo.save(singleMatchHistory);

        return singleMatchHistory.getWinnerId().equals(homeId);
    }


    @Transactional
    public List<GameResultResponse> getMatchHistory() {
        Map<String, AppUser> usersByUserId = userRepository.findAll().stream()
                .collect(toMap(AppUser::getUserId, identity()));
        List<SingleMatchHistory> singleMatchHistories = singleMatchHistoryRepo.findTop1000ByMatchTypeOrderByMatchTimeDesc(matchType);

        return singleMatchHistories.stream()
                .map(a -> GameResultResponse.createSingleGame(a, usersByUserId))
                .toList();
    }


}
