package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.UserRanking;
import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.dto.response.UserProfileResponse;
import com.tmp.xmash.type.Gender;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserRankingRepository userRankingRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserProfileResponse login(String userId, String password) throws BadRequestException {
        AppUser appUser = userRepository.findByUserId(userId).orElseThrow(() -> new BadRequestException("비밀번호 또는 Id가 일치하지않습니다."));

        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            throw new BadRequestException("비밀번호 또는 Id가 일치하지않습니다.");
        }

        return new UserProfileResponse(appUser.getUserId(),
                appUser.getName(),
                appUser.getEmail(),
                appUser.getGender(),
                "");
    }

    @Transactional
    public void signUp(String userId, String password, String email, String name, Gender gender) {
        // AppUser 생성
        AppUser appUser = new AppUser(
                userId,
                passwordEncoder.encode(password),
                email,
                gender,
                name
        );

        // UserRanking 생성 (AppUser와 연관 관계 설정)
        UserRanking userRanking = UserRanking.createDefault(appUser);

        userRepository.save(appUser);  // AppUser 먼저 저장 (ID가 필요할 수 있으므로)
        userRanking = userRankingRepository.save(userRanking);  // UserRanking 저장
        appUser.addUserRanking(userRanking);  // AppUser에 UserRanking 추가
        userRepository.save(appUser);
    }
}
