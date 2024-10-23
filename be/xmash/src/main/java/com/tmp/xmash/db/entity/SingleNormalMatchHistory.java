package com.tmp.xmash.db.entity;

import com.tmp.xmash.util.XmashTimeCreator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SingleNormalMatchHistory {

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
    public SingleNormalMatchHistory(String winnerId, String loserId, int winnerScore, int loserScore) {
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
        this.matchTime = XmashTimeCreator.getCurrentTimeUTC();
    }
}
