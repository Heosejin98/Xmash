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

    private int ranking;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    private int lp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", unique = true)
    private AppUser appUser;

}
