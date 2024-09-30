package com.tmp.xmash.service.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameServiceConfig {

    // GameServiceFactory 빈 등록
    @Bean
    public GameServiceFactory gameServiceFactory(
            @Qualifier("normalGameService") GameService normalGameService,
            @Qualifier("singleNormalGameService") GameService singleGameService,
            @Qualifier("doubleNormalGameService") GameService doubleGameService,
            @Qualifier("singleRankGameService") GameService singleRankGameService,
            @Qualifier("doubleRankGameService") GameService doubleRankGameService,
            @Qualifier("rankGameService") GameService rankGameService
    ) {
        return new GameServiceFactory(normalGameService,
                singleGameService,
                doubleGameService,
                singleRankGameService,
                doubleRankGameService,
                rankGameService);
    }

}
