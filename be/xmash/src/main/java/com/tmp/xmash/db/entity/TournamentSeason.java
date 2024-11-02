package com.tmp.xmash.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_season_id")
    @BatchSize(size = 64)
    private Set<DoubleTournamentMatchResult> doubleTournamentMatchResults = new HashSet<>();

}
