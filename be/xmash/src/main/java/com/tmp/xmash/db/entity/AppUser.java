package com.tmp.xmash.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@ToString
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String email;
    private String name;
    private String password;
    private String gender;

    protected AppUser() {}

}
