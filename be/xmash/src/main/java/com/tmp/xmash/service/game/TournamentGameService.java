package com.tmp.xmash.service.game;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.DoubleTournamentMatchResult;
import com.tmp.xmash.db.entity.TournamentRegistration;
import com.tmp.xmash.db.entity.TournamentSeason;
import com.tmp.xmash.db.repositroy.TournamentRegistrationRepo;
import com.tmp.xmash.db.repositroy.TournamentSeasonRepo;
import com.tmp.xmash.dto.response.SetGameResultResponse;
import com.tmp.xmash.dto.response.TournamentGameResponse;
import com.tmp.xmash.exption.AuthenticationException;
import com.tmp.xmash.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TournamentGameService {

    private final TournamentRegistrationRepo tournamentRegistrationRepo;
    private final TournamentSeasonRepo tournamentSeasonRepo;
    private final UserService userService;

    @Transactional
    public void registration(String userId)  {
        if (userId == null) throw new AuthenticationException("로그인 세션 만료");

        AppUser appUser = userService.findByUserId(userId);
        TournamentSeason tournamentSeason = tournamentSeasonRepo.findByCurrentSeasonTrue();
        TournamentRegistration tournamentRegistration = new TournamentRegistration(tournamentSeason.getSeason(), appUser);
        tournamentRegistrationRepo.save(tournamentRegistration);
    }

    @Transactional(readOnly = true)
    public List<TournamentGameResponse> getTournamentInfo(int season) {
        TournamentSeason tournamentSeason = tournamentSeasonRepo.findBySeasonWithMatchResults(season).orElseThrow();
        Set<DoubleTournamentMatchResult> matchResults = tournamentSeason.getDoubleTournamentMatchResults();
        
        int totalRound = matchResults.size() + 1;

        return matchResults.stream()
                .map(result -> new TournamentGameResponse(
                        totalRound,
                        result.getRound(),
                        result.getHomeTeamId(),
                        result.getAwayTeamId(),
                        result.getHomeTeamPlayers(),
                        result.getAwayTeamPlayers(),
                        result.getMatchHistories().stream()
                                .map(SetGameResultResponse::from)
                                .toList(),
                        result.getWinnerTeamId()
                ))
                .sorted(Comparator
                    .comparing(TournamentGameResponse::round).reversed()
                    .thenComparing(r -> r.winnerId() == null)
                ).toList();
    }

}
