package com.tmp.xmash.db.entity;

import com.tmp.xmash.type.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.tmp.xmash.common.AppConstants.CURRENT_SEASON;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id")
    private Long id;

    private String userId;

    @Setter
    private String email;

    @Setter
    private String name;

    @Setter
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "app_user_user_ranking_map",  // 연관관계 테이블 이름 명시
            joinColumns = @JoinColumn(name = "app_user_id"),  // AppUser 외래 키
                inverseJoinColumns = @JoinColumn(name = "user_ranking_id")  // UserRanking 외래 키
    )
    private List<UserRanking> userRankings = new ArrayList<>();

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private List<UserTeamRanking> userTeamRankings = new ArrayList<>();

    @Builder
    public AppUser(String userId,
                   String password,
                   String email,
                   Gender gender,
                   String name) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.name = name;
    }

    public void addUserRanking(UserRanking userRanking) {
        this.userRankings.add(userRanking);
    }

    public UserRanking getCurrentUserRanking() {
        return userRankings.stream()
                .filter(userRanking -> userRanking.matchSeason(CURRENT_SEASON))
                .findFirst()
                .orElseThrow();
    }
}
