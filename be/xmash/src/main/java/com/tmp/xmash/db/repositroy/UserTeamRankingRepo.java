package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.UserTeamRanking;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRankingRepo extends JpaRepository<UserTeamRanking, Integer> {

    Set<UserTeamRanking> findByTeamUserId(Long teamUserId);

}
