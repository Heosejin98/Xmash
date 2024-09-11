package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.dto.response.RankingResponse;
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
    private UserRepository userRepository;

    @Autowired
    private RankingService rankingService;

    @Test
    void getRankingTest() {
        // given
        List<AppUser> appUser = List.of(
                new AppUser("test1", "qwe"),
                new AppUser("test2", "qwe"),
                new AppUser("test3", "qwe"),
                new AppUser("test4", "qwe")
        );
        userRepository.saveAll(appUser);

        UserRanking ranking1 = new UserRanking(1L, 3, Tier.SILVER, 10, appUser.get(0));
        UserRanking ranking2 = new UserRanking(2L, 1, Tier.DIAMOND, 100, appUser.get(1));
        UserRanking ranking3 = new UserRanking(3L, 2, Tier.GOLD, 50, appUser.get(2));
        UserRanking ranking4 = new UserRanking(4L, 4, Tier.SILVER, 10, appUser.get(3));
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