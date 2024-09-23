package com.tmp.xmash.db.entity;


import com.tmp.xmash.type.Tier;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Enumerated(EnumType.STRING)
    private Tier tier;

    private int lp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", unique = true)
    private AppUser appUser;

    public UserRanking(Tier tier, int lp, AppUser appUser) {
        this.tier = tier;
        this.lp = lp;
        this.appUser = appUser;
    }

    public void updateLp(int lp) {
        this.lp = lp;
    }

    public static UserRanking createDefault(AppUser appUser) {
        return new UserRanking(Tier.GOLD, 1000, appUser);
    }
}
