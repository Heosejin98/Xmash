package com.tmp.xmash.service.game.Impl;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;
import com.tmp.xmash.db.repositroy.DoubleRankMatchRepo;
import com.tmp.xmash.domain.DoubleMatchEvaluator;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.domain.RequestUserRanking;
import com.tmp.xmash.dto.request.GameResultRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.exption.BadRequestException;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.service.UserService;
import com.tmp.xmash.service.game.GamePostAble;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.util.XmashTimeCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class DoubleGameService implements GameService, GamePostAble {

    private static final MatchType MATCH_TYPE = MatchType.DOUBLE;

    private final DoubleRankMatchRepo doubleRankMatchRepo;
    private final UserService userService;
    private final RankingService rankingService;


    @Transactional
    @Override
    public boolean matchDone(MatchEvaluator matchEvaluator)  {
        DoubleMatchEvaluator doubleMatchEvaluator = (DoubleMatchEvaluator) matchEvaluator;
        matchEvaluator.checkScore();

        List<AppUser> matchUsers = userService.findByUserIdIn(doubleMatchEvaluator.getUserIds());
        RequestUserRanking requestUserRanking = getRequestUserRanking(doubleMatchEvaluator, matchUsers);
        doubleRankMatchRepo.save(doubleMatchEvaluator.resolveRankMatchWinner());

        if (doubleMatchEvaluator.isHomeWinner()) {
            rankingService.updateRanking(requestUserRanking.winnerRankings(), requestUserRanking.loserRankings(), doubleMatchEvaluator.getResultLp());
            return true;
        }

        rankingService.updateRanking(requestUserRanking.winnerRankings(), requestUserRanking.loserRankings(), doubleMatchEvaluator.getResultLp());
        return false;
    }

    @Override
    public void modifyMatchHistory(GameResultRequest gameModifyRequest, long matchId) {
        LocalDateTime tenMinutesAgo = XmashTimeCreator.getCurrentTimeUTC().minusMinutes(10);
        DoubleMatchEvaluator doubleMatchEvaluator = (DoubleMatchEvaluator) gameModifyRequest.toMatchEvaluator();
        DoubleRankMatchHistory matchHistory = doubleRankMatchRepo.findById(matchId).orElseThrow();
        if (matchHistory.getMatchTime().isBefore(tenMinutesAgo)) {
            throw new BadRequestException("10분 이전 데이터는 수정할 수 없습니다.");
        }

        int prevLp = matchHistory.getLp();

        //Ranking 원복
        List<AppUser> oldMatchUsers = userService.findByUserIdIn(doubleMatchEvaluator.getUserIds());
        RequestUserRanking prevRequestUserRanking = getRequestUserRanking(doubleMatchEvaluator, oldMatchUsers);
        rankingService.updateRanking(prevRequestUserRanking.winnerRankings(), prevRequestUserRanking.loserRankings(), matchHistory.getLp() * -1);

        //MatchHistory 수정 값으로 update
        DoubleRankMatchHistory doubleRankMatchHistory = doubleMatchEvaluator.resolveRankMatchWinner();
        matchHistory.updateMatchHistory( //기존 history 신규 객체로 Update
                doubleRankMatchHistory.getWinner1Id(),
                doubleRankMatchHistory.getWinner2Id(),
                doubleRankMatchHistory.getLoser1Id(),
                doubleRankMatchHistory.getLoser2Id(),
                doubleRankMatchHistory.getWinnerScore(),
                doubleRankMatchHistory.getLoserScore()
        );

        //Ranking 수정한 값에 따라 변동
        List<AppUser> newMatchUsers = userService.findByUserIdIn(doubleMatchEvaluator.getUserIds());
        RequestUserRanking newRequestUserRanking = getRequestUserRanking(doubleMatchEvaluator, newMatchUsers);
        rankingService.updateRanking(newRequestUserRanking.winnerRankings(), newRequestUserRanking.loserRankings(), prevLp);
    }

    @Override
    public List<GameResultResponse> getMatchHistory() {
        Map<String, AppUser> usersByUserId = userService.findAll().stream()
                .collect(toMap(AppUser::getUserId, identity()));
        List<DoubleRankMatchHistory> singleMatchHistories = doubleRankMatchRepo.findTop1000ByOrderByMatchTimeDesc();

        return singleMatchHistories.stream()
                .map(matchHistory -> GameResultResponse.createDoubleRankGame(matchHistory, usersByUserId, MATCH_TYPE))
                .toList();
    }
}
