package com.tmp.xmash.service.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.tmp.xmash.type.MatchType;

@Configuration
public class GameServiceFactory {

    private final GameService singleGameService;
    private final GameService doubleGameService;
    private final GameService totalGameService;

    public GameServiceFactory(
            @Qualifier("singleGameService") GameService singleGameService,
            @Qualifier("doubleGameService") GameService doubleGameService,
            @Qualifier("rankGameService") GameService totalGameService
    ) {
        this.singleGameService = singleGameService;
        this.doubleGameService = doubleGameService;
        this.totalGameService = totalGameService;
    }

    public GameService getGameService(MatchType matchType) {
        return switch (matchType) {
            case SINGLE -> singleGameService;
            case DOUBLE -> doubleGameService;
            case ALL -> totalGameService;
        };
    }

    public GamePostAble getGamePostAble(MatchType matchType) {
        return switch (matchType) {
            case SINGLE -> (GamePostAble) singleGameService;
            case DOUBLE -> (GamePostAble) doubleGameService;
            default -> throw new IllegalArgumentException("게임 등록이 불가능한 매치 타입입니다: " + matchType);
        };
    }
}