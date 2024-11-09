package com.tmp.xmash.db.entity;

import com.tmp.xmash.util.XmashTimeCreator;
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

    @Column(nullable = false)
    private LocalDateTime matchTime;

    @Column(nullable = false)
    private String winnerId;

    @Column(nullable = false)
    private String loserId;

    @Column(nullable = false)
    private int winnerScore;

    @Column(nullable = false)
    private int loserScore;

    @Column(nullable = false)
    private int lp;

    @Builder
    public SingleRankMatchHistory(String winnerId,
                                  String loserId,
                                  int winnerScore,
                                  int loserScore,
                                  int lp
    ) {
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
        this.matchTime = XmashTimeCreator.getCurrentTimeUTC();
        this.lp = lp;
    }

    public void updateMatchHistory(String winnerId, String loserId,
                                    int winnerScore, int loserScore) {
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
    }
}
