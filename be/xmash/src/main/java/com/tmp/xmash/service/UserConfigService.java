package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.dto.request.PasswordUpdateRequest;
import com.tmp.xmash.dto.request.UserProfileRequest;
import com.tmp.xmash.dto.response.PlayerResponse;
import com.tmp.xmash.dto.response.UserProfileResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserConfigService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


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
    public UserProfileResponse updateUserPassword(PasswordUpdateRequest passwordUpdateReq, String userId) throws AuthenticationException, BadRequestException {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new AuthenticationException("User not found"));


        if (!passwordEncoder.matches(passwordUpdateReq.prevPassword(), appUser.getPassword())) {
            throw new BadRequestException("Password not matched");
        }

        if (passwordEncoder.matches(passwordUpdateReq.newPassword(), appUser.getPassword())) {
            throw new BadRequestException("이전 패스워드와 다른 비밀번호 입력하세요.");
        }


        appUser.setPassword(passwordEncoder.encode(passwordUpdateReq.newPassword()));
        userRepository.save(appUser);

        return new UserProfileResponse(appUser.getUserId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getGender(),
                "");
    }

}
