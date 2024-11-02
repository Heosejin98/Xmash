package com.tmp.xmash.db.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tmp.xmash.dto.response.PlayerResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

/**
 *
 * @author sejin
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoubleTournamentMatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "double_tournament_match_result_id")
    private Long id;

    private int round;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_result_id")
    @BatchSize(size = 5)
    private Set<DoubleTournamentMatchHistory> matchHistories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "win_team_id")
    private TournamentTeam winTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    private TournamentTeam homeTeam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    private TournamentTeam awayTeam;

    private LocalDateTime matchTime;

    public List<PlayerResponse> getHomeTeamPlayers() {
        if (getHomeTeam() == null) return new ArrayList<>();

        List<PlayerResponse> players = new ArrayList<>();
        players.add(PlayerResponse.from(awayTeam.getUser1()));
        players.add(PlayerResponse.from(awayTeam.getUser2()));
        
        return players;
    }

    public List<PlayerResponse> getAwayTeamPlayers() {
        if (getAwayTeam() == null) return new ArrayList<>();

        List<PlayerResponse> players = new ArrayList<>();
        players.add(PlayerResponse.from(homeTeam.getUser1()));
        players.add(PlayerResponse.from(homeTeam.getUser2()));
    
        return players;
    }

    public Long getWinnerTeamId() {
        if (this.getWinTeam() == null) {
            return null;
        }

        return this.getWinTeam().getId();
    }

    public Long getHomeTeamId() {
        if (this.getHomeTeam() == null) {
            return null;
        }

        return this.getHomeTeam().getId();
    }


    public Long getAwayTeamId() {
        if (this.getAwayTeam() == null) {
            return null;
        }

        return this.getAwayTeam().getId();
    }
}
