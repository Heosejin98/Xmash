package com.tmp.xmash.service.game.Impl;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.SingleRankMatchHistory;
import com.tmp.xmash.db.repositroy.SingleRankMatchHistoryRepo;
import com.tmp.xmash.domain.MatchEvaluator;
import com.tmp.xmash.domain.RequestUserRanking;
import com.tmp.xmash.domain.SingleMatchEvaluator;
import com.tmp.xmash.dto.request.GameResultRequest;
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
    public void modifyMatchHistory(GameResultRequest gameModifyRequest, long matchId) {
        LocalDateTime tenMinutesAgo = XmashTimeCreator.getCurrentTimeUTC().minusMinutes(10);
        SingleMatchEvaluator singleMatchEvaluator = (SingleMatchEvaluator) gameModifyRequest.toMatchEvaluator();
        SingleRankMatchHistory matchHistory = singleMatchHistoryRepo.findById(matchId).orElseThrow();
        if (matchHistory.getMatchTime().isBefore(tenMinutesAgo)) {
            throw new BadRequestException("10분 이전 데이터는 수정할 수 없습니다.");
        }
        int prevLp = matchHistory.getLp();

        //Ranking 원복
        List<AppUser> oldMatchUsers = userService.findByUserIdIn(singleMatchEvaluator.getUserIds());
        RequestUserRanking prevRequestUserRanking = getRequestUserRanking(singleMatchEvaluator, oldMatchUsers);
        rankingService.updateRanking(prevRequestUserRanking.winnerRankings(),
                prevRequestUserRanking.loserRankings(),
                matchHistory.getLp() * -1);

        //MatchHistory 수정 값으로 update
        SingleRankMatchHistory doubleRankMatchHistory = singleMatchEvaluator.resolveMatchWinner();
        matchHistory.updateMatchHistory( //기존 history 신규 객체로 Update
                doubleRankMatchHistory.getWinnerId(),
                doubleRankMatchHistory.getLoserId(),
                doubleRankMatchHistory.getWinnerScore(),
                doubleRankMatchHistory.getLoserScore()
        );

        //Ranking 수정한 값에 따라 변동
        List<AppUser> newMatchUsers = userService.findByUserIdIn(singleMatchEvaluator.getUserIds());
        RequestUserRanking newRequestUserRanking = getRequestUserRanking(singleMatchEvaluator, newMatchUsers);
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
