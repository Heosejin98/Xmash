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

    //TODO : ranking 저장하지말고 ROW_NUMBER()로 조회할 수 있도록 변경 ?
    private int ranking;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    private int lp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", unique = true)
    private AppUser appUser;

    public UserRanking(int ranking, Tier tier, int lp, AppUser appUser) {
        this.ranking = ranking;
        this.tier = tier;
        this.lp = lp;
        this.appUser = appUser;
    }

    public static UserRanking createDefault(AppUser appUser) {
        return new UserRanking(1, Tier.GOLD, 1000, appUser);
    }
}
