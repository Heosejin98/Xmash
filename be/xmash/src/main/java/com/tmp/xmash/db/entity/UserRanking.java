package com.tmp.xmash.db.entity;


import com.tmp.xmash.type.Tier;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public UserRanking(Tier tier, int lp, AppUser appUser) {
        this.tier = tier;
        this.lp = lp;
        this.appUser = appUser;
        this.season = CURRENT_SEASON;
    }

    public boolean matchSeason(int season) {
        return this.season == season;
    }

    public void updateLpAndTier(int lp) {
        this.lp = lp;
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

    public static UserRanking createDefault(AppUser appUser) {
        return new UserRanking(Tier.GOLD, 1000, appUser);
    }
}
