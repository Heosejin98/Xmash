package com.tmp.xmash.db.entity;


import com.tmp.xmash.type.Tier;
import jakarta.persistence.*;
import lombok.*;

import static com.tmp.xmash.common.AppConstants.CURRENT_SEASON;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTeamRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_team_ranking_id")
    private Long id;

    private int season;

    @Setter
    private Long teamUserId;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    @Setter
    private int lp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    private UserTeamRanking(AppUser appUser) {
        this.season = CURRENT_SEASON;
        this.tier = Tier.GOLD;
        this.appUser = appUser;
        this.lp = 1000;
    }

    public static UserTeamRanking createDefault(AppUser appUser) {
        return new UserTeamRanking(appUser);
    }
}
