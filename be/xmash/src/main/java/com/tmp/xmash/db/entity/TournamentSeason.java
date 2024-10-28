package com.tmp.xmash.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TournamentSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_season_id")
    private Long id;

    private int season;

    private String seasonName;

    private boolean currentSeason;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    public TournamentSeason(int season, String seasonName, boolean currentSeason, LocalDateTime startTime, LocalDateTime endTime) {
        this.season = season;
        this.seasonName = seasonName;
        this.currentSeason = currentSeason;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
