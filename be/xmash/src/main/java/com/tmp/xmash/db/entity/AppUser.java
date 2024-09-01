package com.tmp.xmash.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id")
    private Long id;
    private String userId;
    private String email;
    private String name;
    private String password;
    private String gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ranking_id", unique = true)
    private UserRanking userRanking;


    public AppUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
