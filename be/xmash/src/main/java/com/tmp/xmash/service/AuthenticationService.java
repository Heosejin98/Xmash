package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.entity.UserTeamRanking;
import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.db.repositroy.UserTeamRankingRepo;
import com.tmp.xmash.dto.response.UserProfileResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserRankingRepository userRankingRepository;
    private final UserTeamRankingRepo userTeamRankingRepo;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserProfileResponse login(String userId, String password) {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            throw new IllegalArgumentException("Password not matched");
        }

        passwordEncoder.encode(userId);

        return new UserProfileResponse(appUser.getUserId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getGender(),
                "");
    }

    @Transactional
    public void signUp(String userId, String password) {
        AppUser appUser = new AppUser(userId, passwordEncoder.encode(password));
        UserRanking userRanking = UserRanking.createDefault(appUser);
        UserTeamRanking userTeamRanking = UserTeamRanking.createDefault(appUser);
        appUser.updateUserRanking(Collections.singletonList(userRanking));
        userRepository.save(appUser);
        userRankingRepository.save(userRanking);
        userTeamRankingRepo.save(userTeamRanking);
    }
}
