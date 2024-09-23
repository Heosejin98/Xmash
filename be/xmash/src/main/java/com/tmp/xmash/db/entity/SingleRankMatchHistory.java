package com.tmp.xmash.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SingleRankMatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_history_id")
    private Long id;

    private LocalDateTime matchTime;

    private String winnerId;

    private String loserId;

    private int winnerScore;

    private int loserScore;

    @Builder
    public SingleRankMatchHistory(String winnerId, String loserId, int winnerScore, int loserScore) {
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
        this.matchTime = LocalDateTime.now();
    }
}
