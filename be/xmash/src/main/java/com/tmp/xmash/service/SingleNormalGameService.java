package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.SingleMatchHistory;
import com.tmp.xmash.db.repositroy.SingleMatchHistoryRepo;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.dto.GameResultRequest;
import com.tmp.xmash.type.MatchType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SingleNormalGameService {

    private final SingleMatchHistoryRepo singleMatchHistoryRepo;


    @Transactional
    public boolean getMatchResult(GameResultRequest gameResultRequest, String homeId, MatchType matchType) {
        MatchEvaluator matchEvaluator = new MatchEvaluator(homeId, matchType, gameResultRequest);

        SingleMatchHistory singleMatchHistory = matchEvaluator.resolveMatchWinner();
        singleMatchHistoryRepo.save(singleMatchHistory);

        return singleMatchHistory.getWinnerId().equals(homeId);
    }



}
