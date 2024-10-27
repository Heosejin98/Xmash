package com.tmp.xmash.service.game;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.TournamentRegistration;
import com.tmp.xmash.db.entity.TournamentSeason;
import com.tmp.xmash.db.repositroy.TournamentRegistrationRepo;
import com.tmp.xmash.db.repositroy.TournamentSeasonRepo;
import com.tmp.xmash.exption.AuthenticationException;
import com.tmp.xmash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentGameService {

    private final TournamentRegistrationRepo tournamentRegistrationRepo;
    private final TournamentSeasonRepo tournamentSeasonRepo;
    private final UserService userService;

    public void registration(String userId)  {
        if (userId == null) throw new AuthenticationException("로그인 세션 만료");

        AppUser appUser = userService.findByUserId(userId);
        TournamentSeason tournamentSeason = tournamentSeasonRepo.findByCurrentSeasonTrue();
        TournamentRegistration tournamentRegistration = new TournamentRegistration(tournamentSeason.getSeason(), appUser);
        tournamentRegistrationRepo.save(tournamentRegistration);
    }

}
