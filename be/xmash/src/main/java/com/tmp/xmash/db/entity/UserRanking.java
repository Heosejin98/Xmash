package com.tmp.xmash.db.entity;


import com.tmp.xmash.type.Tier;
import jakarta.persistence.*;
import lombok.*;

import static com.tmp.xmash.common.AppConstants.CURRENT_SEASON;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class UserRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ranking_id")
    private Long id;

    private int season;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    private int lp;

    @Enumerated(EnumType.STRING)
    private Tier teamTier;

    private int teamLp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public UserRanking(Tier tier, int lp, AppUser appUser) {
        this.tier = tier;
        this.lp = lp;
        this.teamTier = tier;
        this.teamLp = lp;
        this.appUser = appUser;
        this.season = CURRENT_SEASON;
    }

    public boolean matchSeason(int season) {
        return this.season == season;
    }

    public void updateLpAndTier(int lp) {
        if (this.tier == Tier.UNRANK) {
            this.lp = 1000 + lp;
        } else {
            this.lp += lp;
        }

        if (this.lp > 1300) {
            this.tier = Tier.DIAMOND;
        }

        if (this.lp > 800 && this.lp <= 1300) {
            this.tier = Tier.GOLD;
        }

        if (this.lp <= 700) {
            this.tier = Tier.SILVER;
        }
    }

    public void updateTeamLpAndTeamTier(int lp) {
        this.teamLp = lp;
        if (this.teamLp > 1300) {
            this.teamTier = Tier.DIAMOND;
        }

        if (this.teamLp > 800 && this.teamLp <= 1300) {
            this.teamTier = Tier.GOLD;
        }

        if (this.teamLp <= 700) {
            this.teamTier = Tier.SILVER;
        }
    }

    public static UserRanking createDefault(AppUser appUser) {
        return new UserRanking(Tier.UNRANK, 0, appUser);
    }
}
