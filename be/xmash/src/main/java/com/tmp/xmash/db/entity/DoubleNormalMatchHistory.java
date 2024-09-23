package com.tmp.xmash.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "double_normal_match_history")
public class DoubleNormalMatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "double_normal_match_history_id")
    private Long id;

    @Column(nullable = false)
    private String winner1Id;

    @Column(nullable = false)
    private String winner2Id;

    @Column(nullable = false)
    private String loser1Id;

    @Column(nullable = false)
    private String loser2Id;

    @Column(nullable = false)
    private int winnerScore;

    @Column(nullable = false)
    private int loserScore;

    @Column(nullable = false)
    private LocalDateTime matchTime;

    @Builder
    public DoubleNormalMatchHistory(String winner1Id, String winner2Id, String loser1Id,
            String loser2Id, int winnerScore, int loserScore, LocalDateTime matchTime) {
        this.winner1Id = winner1Id;
        this.winner2Id = winner2Id;
        this.loser1Id = loser1Id;
        this.loser2Id = loser2Id;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
        this.matchTime = matchTime;
    }

}