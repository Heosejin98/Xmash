package com.tmp.xmash.service.game;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.TournamentRegistration;
import com.tmp.xmash.db.entity.TournamentSeason;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TournamentGameServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TournamentSeasonRepo tournamentSeasonRepo;

    @Mock
    private TournamentRegistrationRepo tournamentRegistrationRepo;

    @InjectMocks
    private TournamentGameService registrationService;

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
                .currentSeason(true)
                .build();
    }

    @Test
    @DisplayName("로그인 세션 만료 테스트")
    void RegistrationWithNullUserId() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> registrationService.registration(null));
        assertEquals("로그인 세션 만료", exception.getMessage());
    }

    @Test
    @DisplayName("정상적인 userId로 등록이 성공하는지 확인")
    void RegistrationWithValidUserId() {
        String userId = "testUserId";

        when(userService.findByUserId(userId)).thenReturn(appUser);
        when(tournamentSeasonRepo.findByCurrentSeasonTrue()).thenReturn(currentSeason);

        registrationService.registration(userId);

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

        assertThrows(NullPointerException.class, () -> registrationService.registration(userId));

        verify(userService, times(1)).findByUserId(userId);
        verify(tournamentSeasonRepo, times(1)).findByCurrentSeasonTrue();
        verify(tournamentRegistrationRepo, never()).save(any(TournamentRegistration.class));
    }

}