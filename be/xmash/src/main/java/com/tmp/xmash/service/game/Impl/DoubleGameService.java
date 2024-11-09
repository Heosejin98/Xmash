package com.tmp.xmash.service.game.Impl;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.tmp.xmash.domain.RequestUserRanking;
import com.tmp.xmash.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleRankMatchHistory;
import com.tmp.xmash.db.repositroy.DoubleRankMatchRepo;
import com.tmp.xmash.domain.DoubleMatchEvaluator;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.dto.request.GameModifyRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.exption.BadRequestException;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.service.game.GamePostAble;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.util.XmashTimeCreator;

import lombok.RequiredArgsConstructor;

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
    public void modifyMatchHistory(GameModifyRequest gameModifyRequest, long matchId) {
        LocalDateTime tenMinutesAgo = XmashTimeCreator.getCurrentTimeUTC().minusMinutes(10);
    
        if (gameModifyRequest.matchTime().isBefore(tenMinutesAgo)) {
            throw new BadRequestException("10분 이전 데이터는 수정할 수 없습니다.");
        }
        DoubleRankMatchHistory matchHistory = doubleRankMatchRepo.findById(matchId).orElseThrow();
        int prevLp = matchHistory.getLp();

        //Ranking 원복
        resetRanking(gameModifyRequest, prevLp);
        //MatchHistory 수정 값으로 update
        DoubleMatchEvaluator doubleMatchEvaluator = updateMatchHistory(gameModifyRequest, matchHistory);
        //Ranking 수정한 값에 따라 변동
        updateRanking(prevLp, doubleMatchEvaluator);
    }

    private void resetRanking(GameModifyRequest gameModifyRequest, int prevLp) {
        DoubleMatchEvaluator doubleMatchEvaluator = new DoubleMatchEvaluator(
                gameModifyRequest.prevWinnerUserIds(),
                gameModifyRequest.prevLoserUserIds(),
                0,
                0
        );
        List<AppUser> matchUsers = userService.findByUserIdIn(doubleMatchEvaluator.getUserIds());
        RequestUserRanking prevRequestUserRanking = getRequestUserRanking(doubleMatchEvaluator, matchUsers);
        rankingService.updateRanking(prevRequestUserRanking.winnerRankings(), prevRequestUserRanking.loserRankings(), prevLp * -1);
    }

    private DoubleMatchEvaluator updateMatchHistory(GameModifyRequest gameModifyRequest, DoubleRankMatchHistory matchHistory) {
        //Match History Update
        DoubleMatchEvaluator doubleMatchEvaluator = new DoubleMatchEvaluator(
                gameModifyRequest.homeUserIds(),
                gameModifyRequest.awayUserIds(),
                gameModifyRequest.homeScore(),
                gameModifyRequest.awayScore()
        );
        DoubleRankMatchHistory doubleRankMatchHistory = doubleMatchEvaluator.resolveRankMatchWinner();
        matchHistory.updateMatchHistory( //기존 history 신규 객체로 Update
                doubleRankMatchHistory.getWinner1Id(),
                doubleRankMatchHistory.getWinner2Id(),
                doubleRankMatchHistory.getLoser1Id(),
                doubleRankMatchHistory.getLoser2Id(),
                doubleRankMatchHistory.getWinnerScore(),
                doubleRankMatchHistory.getLoserScore()
        );

        return doubleMatchEvaluator;
    }

    private void updateRanking(int prevLp, DoubleMatchEvaluator doubleMatchEvaluator) {
        List<AppUser> matchUsers = userService.findByUserIdIn(doubleMatchEvaluator.getUserIds());
        RequestUserRanking newRequestUserRanking = getRequestUserRanking(doubleMatchEvaluator, matchUsers);
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
