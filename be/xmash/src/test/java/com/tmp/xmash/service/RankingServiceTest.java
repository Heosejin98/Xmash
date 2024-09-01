package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.dto.RankingResponse;
import com.tmp.xmash.type.Tier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@Import(RankingService.class) // Import the service to be used in the test
class RankingServiceTest {

    @Autowired
    private UserRankingRepository userRankingRepository;

    @Autowired
    private RankingService rankingService;

    @Test
    void getRankingTest() {
        // given
        UserRanking ranking1 = new UserRanking(1L, 3, Tier.SILVER, 10, null);
        UserRanking ranking2 = new UserRanking(2L, 1, Tier.DIAMOND, 100, null);
        UserRanking ranking3 = new UserRanking(3L, 2, Tier.GOLD, 50, null);
        UserRanking ranking4 = new UserRanking(4L, 4, Tier.SILVER, 10, null);

        userRankingRepository.saveAll(List.of(ranking1, ranking2, ranking3, ranking4));

        // when
        List<RankingResponse> actualRanking = rankingService.getRanking();

        // then
        assertNotNull(actualRanking);
        assertEquals(actualRanking.getFirst().rank(), 1);
        assertEquals(actualRanking.get(1).rank(), 2);
        assertEquals(actualRanking.get(2).rank(), 3);
        assertEquals(actualRanking.getLast().rank(), 4);
    }
}