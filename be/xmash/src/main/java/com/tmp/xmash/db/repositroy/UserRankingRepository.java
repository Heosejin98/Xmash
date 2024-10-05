package com.tmp.xmash.db.repositroy;


import com.tmp.xmash.db.entity.UserRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRankingRepository extends JpaRepository<UserRanking, Long> {

    List<UserRanking> findAllByOrderByLpDesc();
}
