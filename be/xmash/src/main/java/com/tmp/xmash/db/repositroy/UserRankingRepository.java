package com.tmp.xmash.db.repositroy;


import com.tmp.xmash.db.entity.UserRanking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRankingRepository extends JpaRepository<UserRanking, Long> {

    List<UserRanking> findAllByOrderByRankingDesc();
}
