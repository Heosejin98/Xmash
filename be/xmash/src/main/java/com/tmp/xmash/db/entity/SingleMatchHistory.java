package com.tmp.xmash.db.entity;

import com.tmp.xmash.type.MatchType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SingleMatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "single_match_history_id")
    private Long id;

    private LocalDateTime matchTime;

    private String winnerId;

    private String loserId;

    private int winnerScore;

    private int loserScore;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Builder
    public SingleMatchHistory(String winnerId, String loserId, int winnerScore, int loserScore, MatchType matchType) {
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
        this.matchType = matchType;
        this.matchTime = LocalDateTime.now();
    }
}
