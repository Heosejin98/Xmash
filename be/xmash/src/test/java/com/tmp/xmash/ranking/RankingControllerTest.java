package com.tmp.xmash.ranking;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.dto.response.RankingResponse;
import com.tmp.xmash.service.RankingService;
import com.tmp.xmash.type.MatchType;
import com.tmp.xmash.type.Tier;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import(RankingService.class)
@Sql("/data/ranking.sql")
public class RankingControllerTest {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private UserRankingRepository userRankingRepository;


    @Test
    @DisplayName("단식 랭킹 조회 테스트")
    public void getSingleRankingTest() throws BadRequestException {
        // given
        List<RankingResponse> expectedRankingResponseList  = List.of(
                new RankingResponse( "user5", "user5", 1400, 1,Tier.SILVER),
                new RankingResponse( "user4", "user4", 1300,2, Tier.SILVER),
                new RankingResponse( "user3", "user3", 1200, 3, Tier.SILVER),
                new RankingResponse("user2", "user2", 1100, 4,Tier.SILVER),
                new RankingResponse("user1", "user1", 1000, 5, Tier.SILVER)
        );


        // when
        List<RankingResponse> rankingResponse = rankingService.getRanking(MatchType.SINGLE);

        // then
        for (int i = 0; i < rankingResponse.size(); i++) {
            assertEquals(expectedRankingResponseList.get(i), rankingResponse.get(i), "i번째 요소가 다릅니다: " + i);
        }
    }

    @Test
    @DisplayName("복식 랭킹 조회 테스트")
    public void getDoubleRankingTest() throws BadRequestException {
        // given
        List<RankingResponse> expectedRankingResponseList  = List.of(
                new RankingResponse( "user1", "user1", 1400, 1,Tier.SILVER),
                new RankingResponse( "user2", "user2", 1300,2, Tier.SILVER),
                new RankingResponse( "user3", "user3", 1200, 3, Tier.SILVER),
                new RankingResponse("user4", "user4", 1100, 4,Tier.SILVER),
                new RankingResponse("user5", "user5", 1000, 5, Tier.SILVER)
        );
        // when
        List<RankingResponse> rankingResponse = rankingService.getRanking(MatchType.DOUBLE);

        // then
        for (int i = 0; i < rankingResponse.size(); i++) {
            assertEquals(expectedRankingResponseList.get(i), rankingResponse.get(i), "i번째 요소가 다릅니다: " + i);
        }
    }

    @Test
    @DisplayName("복식 랭킹 조회 테스트")
    public void getRankingThrowTest() {
        assertThatThrownBy(() -> rankingService.getRanking(MatchType.ALL))
                .isInstanceOf(BadRequestException.class);
    }
}
