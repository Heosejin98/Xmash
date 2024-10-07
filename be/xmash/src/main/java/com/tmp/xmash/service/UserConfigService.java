package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.dto.request.UserProfileRequest;
import com.tmp.xmash.dto.response.PlayerResponse;
import com.tmp.xmash.dto.response.UserProfileResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserConfigService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserProfileResponse getUserInfo(String userId) {
        if (userId == null) {
            return null;
        }

        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserProfileResponse(appUser.getUserId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getGender(),
                "");
    }


    @Transactional
    public UserProfileResponse getUserProfile(String userId) {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserProfileResponse(appUser.getUserId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getGender(),
                "");
    }

    @Transactional
    public List<PlayerResponse> getUserInfos() {
        List<AppUser> appUser = userRepository.findAll();

        return appUser.stream().map(a -> new PlayerResponse(a.getUserId(), a.getName(), "")).toList();
    }

    @Transactional
    public UserProfileResponse updateUserInfo(UserProfileRequest userReq) {
        AppUser appUser = userRepository.findByUserId(userReq.userId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        appUser.setName(userReq.userName());
        appUser.setEmail(userReq.userEmail());

        return new UserProfileResponse(appUser.getUserId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getGender(),
                "");
    }


    @Transactional
    public UserProfileResponse updateUserPassword(UserProfileRequest userReq, String userId) {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        appUser.setPassword(passwordEncoder.encode(userReq.password()));
        userRepository.save(appUser);

        return new UserProfileResponse(appUser.getUserId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getGender(),
                "");
    }

}
