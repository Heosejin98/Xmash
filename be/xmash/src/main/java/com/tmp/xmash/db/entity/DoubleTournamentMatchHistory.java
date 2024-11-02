package com.tmp.xmash.db.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author sejin
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoubleTournamentMatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "double_tournament_match_history_id")
    private Long id;

    private int matchSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "win_team_id")
    private TournamentTeam winTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lose_team_id")
    private TournamentTeam loseTeam;

    private int winnerScore;
    
    private int loserScore;

    private LocalDateTime matchTime;
}