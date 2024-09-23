package com.tmp.xmash.service;

import static java.util.stream.Collectors.toList;

import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.dto.response.RankingResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RankingService {

    private final UserRankingRepository userRankingRepository;


    @Transactional(readOnly = true)
    public List<RankingResponse> getRanking() {
        AtomicInteger index = new AtomicInteger(1);

        return userRankingRepository.findAllByOrderByLpDesc().stream()
                .map(ranking -> RankingResponse.from(ranking, index.getAndIncrement()))
                .collect(toList());
    }


    @Transactional
    public void updateRanking(UserRanking winner, UserRanking loser, int resultLp) {
        winner.updateLpAndTier(winner.getLp() + resultLp);
        loser.updateLpAndTier(loser.getLp() - resultLp);
        userRankingRepository.save(winner);
        userRankingRepository.save(loser);
    }

}
