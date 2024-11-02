package com.tmp.xmash.service.game;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

import java.util.*;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.TournamentRegistration;
import com.tmp.xmash.db.repositroy.TournamentRegistrationRepo;
import com.tmp.xmash.db.repositroy.TournamentSeasonRepo;
import com.tmp.xmash.exption.AuthenticationException;
import com.tmp.xmash.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tmp.xmash.db.entity.DoubleTournamentMatchResult;
import com.tmp.xmash.db.entity.TournamentSeason;
import com.tmp.xmash.dto.response.TournamentGameResponse;

/**
 *
 * @author sejin
 */
@ExtendWith(MockitoExtension.class)
public class TournamentGameServiceTest {

    @InjectMocks
    private TournamentGameService tournamentGameService;

    @Mock
    private UserService userService;

    @Mock
    private TournamentSeasonRepo tournamentSeasonRepo;

    @Mock
    private TournamentRegistrationRepo tournamentRegistrationRepo;

    private AppUser appUser;
    private TournamentSeason currentSeason;

    @BeforeEach
    void setUp() {
        appUser = AppUser.builder()
                .userId("testUserId")
                .build();
        currentSeason = TournamentSeason
                .builder()
                .season(1)
                .doubleTournamentMatchResults(new HashSet<>())
                .currentSeason(true)
                .build();
    }

    @Test
    @DisplayName("로그인 세션 만료 테스트")
    void RegistrationWithNullUserId() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> tournamentGameService.registration(null));
        assertEquals("로그인 세션 만료", exception.getMessage());
    }

    @Test
    @DisplayName("정상적인 userId로 등록이 성공하는지 확인")
    void RegistrationWithValidUserId() {
        String userId = "testUserId";

        when(userService.findByUserId(userId)).thenReturn(appUser);
        when(tournamentSeasonRepo.findByCurrentSeasonTrue()).thenReturn(currentSeason);

        tournamentGameService.registration(userId);

        verify(userService, times(1)).findByUserId(userId);
        verify(tournamentSeasonRepo, times(1)).findByCurrentSeasonTrue();
        verify(tournamentRegistrationRepo, times(1)).save(any(TournamentRegistration.class));
    }

    @Test
    @DisplayName("현재 시즌이 없는 경우 예외가 발생하는지 확인")
    void RegistrationWithNoCurrentSeason() {
        String userId = "testUserId";

        when(userService.findByUserId(userId)).thenReturn(appUser);
        when(tournamentSeasonRepo.findByCurrentSeasonTrue()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> tournamentGameService.registration(userId));

        verify(userService, times(1)).findByUserId(userId);
        verify(tournamentSeasonRepo, times(1)).findByCurrentSeasonTrue();
        verify(tournamentRegistrationRepo, never()).save(any(TournamentRegistration.class));
    }

    @Test
    @DisplayName("토너먼트 정보 조회 시 라운드 내림차순, 승자 있는 경기 우선 정렬")
    void getTournamentInfo_ShouldSortByRoundDescAndWinnerFirst() {
        // given
        int season = 1;
        TournamentSeason tournamentSeason = mock(TournamentSeason.class);

        // 테스트용 매치 결과 생성
        DoubleTournamentMatchResult match1 = createMatchResult(4, 1L); // round 2, 승자 있음
        DoubleTournamentMatchResult match2 = createMatchResult(4, null); // round 2, 승자 없음
        DoubleTournamentMatchResult match3 = createMatchResult(2, 2L); // round 1, 승자 있음
        Set<DoubleTournamentMatchResult> matchResults = new HashSet<>(List.of(match1, match2, match3));

        when(tournamentSeasonRepo.findBySeasonWithMatchResults(season))
                .thenReturn(Optional.of(tournamentSeason));
        when(tournamentSeason.getDoubleTournamentMatchResults())
                .thenReturn(matchResults);

        // when
        List<TournamentGameResponse> result = tournamentGameService.getTournamentInfo(season);

        // then
        assertThat(result).hasSize(3);
        // round 2, 승자 있음이 첫 번째
        assertThat(result.get(0).round()).isEqualTo(4);
        assertThat(result.get(0).winnerId()).isNotNull();
        // round 2, 승자 없음이 두 번째
        assertThat(result.get(1).round()).isEqualTo(4);
        assertThat(result.get(1).winnerId()).isNull();
        // round 1, 승자 있음이 세 번째
        assertThat(result.get(2).round()).isEqualTo(2);
        assertThat(result.get(2).winnerId()).isNotNull();
    }

    private DoubleTournamentMatchResult createMatchResult(int round, Long winnerId) {
        DoubleTournamentMatchResult result = mock(DoubleTournamentMatchResult.class);
        when(result.getRound()).thenReturn(round);
        when(result.getWinnerTeamId()).thenReturn(winnerId);
        when(result.getMatchHistories()).thenReturn(new HashSet<>());
        when(result.getHomeTeamPlayers()).thenReturn(new ArrayList<>());
        when(result.getAwayTeamPlayers()).thenReturn(new ArrayList<>());
        return result;
    }

}
