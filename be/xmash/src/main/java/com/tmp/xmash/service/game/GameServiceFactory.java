package com.tmp.xmash.service.game;

import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameServiceFactory {

    private final GameService singleGameService;
    private final GameService teamGameService;

    public GameServiceFactory(
            @Qualifier("singleNormalGameService") GameService singleGameService,
            @Qualifier("doubleNormalGameService") GameService teamGameService
    ) {
        this.singleGameService = singleGameService;
        this.teamGameService = teamGameService;
    }

    public GameService getGameService(MatchType matchType, GameType gameType) {
        return switch (matchType) {
            case SINGLE -> singleGameService;
            case DOUBLE -> teamGameService;
        };
    }
}