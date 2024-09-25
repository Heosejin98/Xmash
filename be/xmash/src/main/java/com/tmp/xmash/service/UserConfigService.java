package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.dto.request.UserProfileRequest;
import com.tmp.xmash.dto.response.PlayerResponse;
import com.tmp.xmash.dto.response.UserInfoResponse;
import com.tmp.xmash.dto.response.UserProfileResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserConfigService {

    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponse getUserInfo(String userId) {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserInfoResponse(appUser.getUserId(), appUser.getName(), appUser.getGender());
    }


    @Transactional
    public UserProfileResponse getUserProfile(String userId) {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserProfileResponse(appUser.getUserId(), appUser.getName(), appUser.getEmail(), "");
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

        return new UserProfileResponse(appUser.getUserId(), appUser.getName(), appUser.getEmail(), "");
    }

}
