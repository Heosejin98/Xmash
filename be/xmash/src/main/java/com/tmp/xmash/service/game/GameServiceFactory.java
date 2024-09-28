package com.tmp.xmash.service.game;

import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameServiceFactory {

    private final GameService normalGameService;
    private final GameService singleNormalGameService;
    private final GameService doubleNormalGameService;
    private final GameService singleRanklGameService;

    public GameServiceFactory(
            @Qualifier("normalGameService") GameService normalGameService,
            @Qualifier("singleNormalGameService") GameService singleNormalGameService,
            @Qualifier("doubleNormalGameService") GameService doubleNormalGameService,
            @Qualifier("singleRankGameService") GameService singleRanklGameService
    ) {
        this.normalGameService = normalGameService;
        this.singleNormalGameService = singleNormalGameService;
        this.doubleNormalGameService = doubleNormalGameService;
        this.singleRanklGameService = singleRanklGameService;
    }

    public GameService getGameService(MatchType matchType, GameType gameType) {
        return switch (gameType) {
            case NORMAL -> getNormalGameService(matchType);
            case RANK -> getRankGameService(matchType);
        };
    }


    private GameService getNormalGameService(MatchType matchType) {
        if (MatchType.SINGLE == matchType) {
            return singleNormalGameService;
        }

        if (MatchType.DOUBLE == matchType) {
            return doubleNormalGameService;
        }

        if (MatchType.ALL == matchType) {
            return normalGameService;
        }

        throw new IllegalArgumentException("Unknown matchType: " + matchType + " or gameType: " + matchType);
    }

    private GameService getRankGameService(MatchType matchType) {
        if (MatchType.SINGLE == matchType) {
            return singleRanklGameService;
        }

        if (MatchType.DOUBLE == matchType) {
            throw new IllegalArgumentException("미구현");
        }

        if (MatchType.ALL == matchType) {
            throw new IllegalArgumentException("미구현");
        }

        throw new IllegalArgumentException("Unknown matchType: " + matchType + " or gameType: " + matchType);
    }
}