package com.tmp.xmash.service.game;

import com.tmp.xmash.type.GameType;
import com.tmp.xmash.type.MatchType;

public class GameServiceFactory {

    private final GameService singleGameService;
    private final GameService teamGameService;

    public GameServiceFactory(GameService singleGameService, GameService teamGameService) {
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