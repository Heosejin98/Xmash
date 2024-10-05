package com.tmp.xmash.db.entity;

import com.tmp.xmash.type.Gender;
import jakarta.persistence.*;
import lombok.*;

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
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private List<UserRanking> userRankings;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private List<UserTeamRanking> userTeamRankings;

    public AppUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void updateUserRanking(List<UserRanking> userRanking) {
        this.userRankings = userRanking;
    }

    public UserTeamRanking getCurrentUserTeamRanking() {
        return userTeamRankings.stream()
                .filter(userTeamRanking -> userTeamRanking.getSeason() == CURRENT_SEASON)
                .findFirst()
                .orElse(null);
    }

    public UserRanking getCurrentUserRanking() {
        return userRankings.stream()
                .filter(userRanking -> userRanking.matchSeason(CURRENT_SEASON))
                .findFirst()
                .orElseThrow();
    }
}
