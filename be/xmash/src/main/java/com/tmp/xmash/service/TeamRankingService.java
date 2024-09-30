package com.tmp.xmash.service;

import static java.util.stream.Collectors.toSet;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.entity.UserTeamRanking;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.db.repositroy.UserTeamRankingRepo;
import com.tmp.xmash.dto.request.PostTeamRequest;
import com.tmp.xmash.dto.response.UserProfileResponse;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamRankingService {

    private final UserTeamRankingRepo userTeamRankingRepo;
    private final UserRepository userRepository;


    /**
     * 이번 시즌 팀이 없으면 false 있으면 true 리턴
     * @param userId user
     * @return user의 이번 시즌 팀이 없으면 false 있으면 true
     */
    @Transactional
    public boolean hasTeam(String userId) {
        AppUser appUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("세션 만료 or 존재하지 않는 사용자"));

        return appUser.getCurrentUserTeamRanking().getTeamUserId() != null;
    }


    @Transactional
    public UserProfileResponse postTeam(PostTeamRequest teamRequest) {
        Set<String> userIds = Set.of(teamRequest.myId(), teamRequest.teamId());
        Set<AppUser> users = userRepository.findByUserIdIn(userIds);

        AppUser myUser = users.stream()
                .filter(user -> teamRequest.myId().equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 - 내 사용자 없음"));

        AppUser teamUser = users.stream()
                .filter(user -> teamRequest.teamId().equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 - 팀 사용자 없음"));

        // 유저의 랭킹 찾기
        UserTeamRanking myRanking = myUser.getCurrentUserTeamRanking();
        UserTeamRanking teamRanking = teamUser.getCurrentUserTeamRanking();

        if (myRanking == null || teamRanking == null) {
            throw new IllegalArgumentException("잘못된 요청 - 랭킹 없음");
        }

        // 팀 정보 업데이트
        myRanking.setTeamUserId(teamUser.getId());
        teamRanking.setTeamUserId(myUser.getId());
        userTeamRankingRepo.saveAll(List.of(myRanking, teamRanking));

        return new UserProfileResponse(
                teamUser.getUserId(),
                teamUser.getName(),
                teamUser.getEmail(),
                teamUser.getGender(),
                ""
        );
    }

    @Transactional
    public List<UserProfileResponse> getUsersWithoutTeam() {
        Set<UserTeamRanking> withOutTeams = userTeamRankingRepo.findByTeamUserId(null);
        Set<AppUser> withOutTeamUsers = withOutTeams.stream()
                .map(UserTeamRanking::getAppUser)
                .collect(toSet());


        return withOutTeamUsers.stream()
                .map(user -> new UserProfileResponse(
                        user.getUserId(),
                        user.getName(),
                        user.getEmail(),
                        user.getGender(),
                        ""
                ))
                .sorted(Comparator.comparing(UserProfileResponse::userName))
                .collect(Collectors.toList());
    }


    @Transactional
    public UserProfileResponse getMyTeamUser(String userId) {
        AppUser appUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        long teamUserId = appUser.getCurrentUserTeamRanking().getTeamUserId();

        AppUser teamUser = userRepository.findById(teamUserId)
                .orElseThrow(() -> new IllegalArgumentException("팀 사용자 없음"));

        return new UserProfileResponse(
                    teamUser.getUserId(),
                    teamUser.getName(),
                    teamUser.getEmail(),
                    teamUser.getGender(),
            ""
            );
    }

}
