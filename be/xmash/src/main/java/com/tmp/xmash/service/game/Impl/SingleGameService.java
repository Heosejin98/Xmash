package com.tmp.xmash.service.game.Impl;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleRankMatchHistory;
import com.tmp.xmash.db.repositroy.SingleRankMatchHistoryRepo;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.domain.RequestUserRanking;
import com.tmp.xmash.domain.SingleMatchEvaluator;
import com.tmp.xmash.dto.request.GameModifyRequest;
import com.tmp.xmash.dto.response.GameResultResponse;
import com.tmp.xmash.exption.BadRequestException;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.service.UserService;
import com.tmp.xmash.service.game.GamePostAble;
import com.tmp.xmash.service.game.GameService;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.util.XmashTimeCreator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class SingleGameService implements GameService, GamePostAble {

    private static final MatchType matchType = MatchType.SINGLE;

    private final SingleRankMatchHistoryRepo singleMatchHistoryRepo;
    private final RankingService rankingService;
    private final UserService userService;

    @Transactional
    @Override
    public boolean matchDone(MatchEvaluator evaluator) {
        SingleMatchEvaluator singleMatchEvaluator = (SingleMatchEvaluator) evaluator;
        singleMatchEvaluator.checkScore();

        List<AppUser> matchUsers = userService.findByUserIdIn(singleMatchEvaluator.getUserIds());
        RequestUserRanking requestUserRanking = getRequestUserRanking(singleMatchEvaluator, matchUsers);
        singleMatchHistoryRepo.save(singleMatchEvaluator.resolveMatchWinner());

        if (evaluator.isHomeWinner()) {
            rankingService.updateRanking(requestUserRanking.winnerRankings(), requestUserRanking.loserRankings(), singleMatchEvaluator.getResultLp());
            return true;
        }

        rankingService.updateRanking(requestUserRanking.winnerRankings(), requestUserRanking.loserRankings(), singleMatchEvaluator.getResultLp());
        return false;
    }

    @Override
    public void modifyMatchHistory(GameModifyRequest gameModifyRequest, long matchId) {
        LocalDateTime tenMinutesAgo = XmashTimeCreator.getCurrentTimeUTC().minusMinutes(10);

        if (gameModifyRequest.matchTime().isBefore(tenMinutesAgo)) {
            throw new BadRequestException("10분 이전 데이터는 수정할 수 없습니다.");
        }
        SingleRankMatchHistory matchHistory = singleMatchHistoryRepo.findById(matchId).orElseThrow();
        int prevLp = matchHistory.getLp();

        //Ranking 원복
        resetRanking(matchHistory);
        //MatchHistory 수정 값으로 update
        SingleMatchEvaluator singleMatchEvaluator = updateMatchHistory(gameModifyRequest, matchHistory);
        //Ranking 수정한 값에 따라 변동
        updateRanking(prevLp, singleMatchEvaluator);
    }

    private void resetRanking(SingleRankMatchHistory matchHistory) {
        SingleMatchEvaluator prevMatchEvaluator = new SingleMatchEvaluator(
                matchHistory.getWinnerId(),
                matchHistory.getLoserId(),
                0,
                0
        );
        List<AppUser> matchUsers = userService.findByUserIdIn(prevMatchEvaluator.getUserIds());
        RequestUserRanking prevRequestUserRanking = getRequestUserRanking(prevMatchEvaluator, matchUsers);
        rankingService.updateRanking(prevRequestUserRanking.winnerRankings(),
                prevRequestUserRanking.loserRankings(),
                matchHistory.getLp() * -1);
    }

    private SingleMatchEvaluator updateMatchHistory(GameModifyRequest gameModifyRequest, SingleRankMatchHistory matchHistory) {
        //Match History Update
        SingleMatchEvaluator singleMatchEvaluator = new SingleMatchEvaluator(
                gameModifyRequest.homeTeam().userId1(),
                gameModifyRequest.awayTeam().userId1(),
                gameModifyRequest.homeScore(),
                gameModifyRequest.awayScore()
        );
        SingleRankMatchHistory doubleRankMatchHistory = singleMatchEvaluator.resolveMatchWinner();
        matchHistory.updateMatchHistory( //기존 history 신규 객체로 Update
                doubleRankMatchHistory.getWinnerId(),
                doubleRankMatchHistory.getLoserId(),
                doubleRankMatchHistory.getWinnerScore(),
                doubleRankMatchHistory.getLoserScore()
        );

        return singleMatchEvaluator;
    }

    private void updateRanking(int prevLp, SingleMatchEvaluator doubleMatchEvaluator) {
        List<AppUser> matchUsers = userService.findByUserIdIn(doubleMatchEvaluator.getUserIds());
        RequestUserRanking newRequestUserRanking = getRequestUserRanking(doubleMatchEvaluator, matchUsers);
        rankingService.updateRanking(newRequestUserRanking.winnerRankings(), newRequestUserRanking.loserRankings(), prevLp);
    }

    @Transactional
    @Override
    public List<GameResultResponse> getMatchHistory() {
        Map<String, AppUser> usersByUserId = userService.findAll().stream()
                .collect(toMap(AppUser::getUserId, identity()));
        List<SingleRankMatchHistory> singleMatchHistories = singleMatchHistoryRepo.findTop1000ByOrderByMatchTimeDesc();

        return singleMatchHistories.stream()
                .map(a -> GameResultResponse.createSingleGame(a, usersByUserId, matchType))
                .toList();
    }
}
