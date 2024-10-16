package com.tmp.xmash.service;

import static java.util.stream.Collectors.toList;

import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.dto.response.RankingResponse;
import com.tmp.xmash.type.MatchType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RankingService {

    private final UserRankingRepository userRankingRepository;


    @Transactional(readOnly = true)
    public List<RankingResponse> getRanking(MatchType matchType) throws BadRequestException {
        AtomicInteger index = new AtomicInteger(1);

        if (matchType == MatchType.SINGLE) {
            return userRankingRepository.findAllByOrderByLpDesc().stream()
                    .map(ranking -> RankingResponse.createSingleRanking(ranking, index.getAndIncrement()))
                    .collect(toList());
        }

        if (matchType == MatchType.DOUBLE) {
            return userRankingRepository.findAllByOrderByTeamLpDesc().stream()
                    .map(ranking -> RankingResponse.createDoubleRanking(ranking, index.getAndIncrement()))
                    .collect(toList());
        }

        throw new BadRequestException("단식 또는 복식을 입력하세요 match type: " + matchType);
    }


    @Transactional
    public void updateRanking(UserRanking winner, UserRanking loser, int resultLp) {
        winner.updateLpAndTier(winner.getLp() + resultLp);
        loser.updateLpAndTier(loser.getLp() - resultLp);
        Set<UserRanking> userRankings = Set.of(winner, loser);
        userRankingRepository.saveAll(userRankings);
    }

    @Transactional
    public void updateRanking(Set<UserRanking> winner, Set<UserRanking> loser, int resultLp) {
        for (UserRanking userRanking : winner) {
            userRanking.updateTeamLpAndTeamTier(userRanking.getTeamLp() + resultLp);
        }

        for (UserRanking userRanking : loser) {
            userRanking.updateTeamLpAndTeamTier(userRanking.getTeamLp() - resultLp);
        }

        Set<UserRanking> userRankings = new HashSet<>(winner);
        userRankings.addAll(loser);
        userRankingRepository.saveAll(userRankings);
    }

}
