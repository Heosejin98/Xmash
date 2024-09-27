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
        if (MatchType.SINGLE == matchType && GameType.RANK == gameType) {
            return singleRanklGameService;
        }

        if (MatchType.DOUBLE == matchType && GameType.NORMAL == gameType) {
            return doubleNormalGameService;
        }

        if (MatchType.SINGLE == matchType && GameType.NORMAL == gameType) {
            return singleNormalGameService;
        }

        throw new IllegalArgumentException("Unknown matchType: " + matchType + " or gameType: " + gameType);
    }
}