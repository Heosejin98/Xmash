package com.tmp.xmash.service.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameServiceConfig {

    // GameServiceFactory 빈 등록
    @Bean
    public GameServiceFactory gameServiceFactory(
            @Qualifier("singleGameService") GameService singleRankGameService,
            @Qualifier("doubleGameService") GameService doubleRankGameService,
            @Qualifier("rankGameService") GameService rankGameService
    ) {
        return new GameServiceFactory(
                singleRankGameService,
                doubleRankGameService,
                rankGameService
        );
    }

}
