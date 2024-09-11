package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.dto.response.UserInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserConfigService {

    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponse getUserInfo(String userId) {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserInfoResponse(appUser.getUserId(), appUser.getName(), appUser.getGender());
    }

}
