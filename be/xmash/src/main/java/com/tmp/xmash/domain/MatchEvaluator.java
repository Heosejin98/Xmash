package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.exption.GamePostException;
import lombok.Getter;

import java.util.List;

import static com.tmp.xmash.common.AppConstants.DEFAULT_WINNER_LP;
import static com.tmp.xmash.common.AppConstants.RANDOM_GENERATOR;

@Getter
public abstract class MatchEvaluator {

    protected final int homeScore;

    protected final int awayScore;

    protected final int resultLp;

    protected final Team homeTeam;

    protected final Team awayTeam;

    protected MatchEvaluator(int homeScore, int awayScore, int resultLp, Team homeTeam, Team awayTeam) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.resultLp = resultLp;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public boolean isHomeWinner() {
        return homeScore > awayScore;
    }

    public void checkScore() {
        if (isDraw()) {
            throw new GamePostException("무승부는 등록 불가능합니다.");
        }

        if (isScoreBelowMinimum()) {
            throw new GamePostException("11점 이상 게임만 등록 가능합니다.");
        }
    }

    public abstract List<String> getUserIds();

    public abstract List<AppUser> getHomeUser(List<AppUser> matchUsers);

    public abstract List<AppUser> getAwayUser(List<AppUser> matchUsers);

    protected static int crateResultLp(int homeScore, int awayScore) {
        int winnerScore = Math.max(homeScore, awayScore);

        if (winnerScore <= 11) {
            return DEFAULT_WINNER_LP;
        }

        if (winnerScore <= 20) {
            return DEFAULT_WINNER_LP + RANDOM_GENERATOR.nextInt(5);
        }

        return DEFAULT_WINNER_LP + (RANDOM_GENERATOR.nextInt(5) * 2);
    }

    private boolean isScoreBelowMinimum() {
        int winnerScore = Math.max(homeScore, awayScore);
        return winnerScore < 11;
    }

    private boolean isDraw() {
        return homeScore == awayScore;
    }
}
