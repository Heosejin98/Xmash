package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.SingleRankMatchHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleRankMatchHistoryRepo extends JpaRepository<SingleRankMatchHistory, Long> {

    List<SingleRankMatchHistory> findTop1000ByOrderByMatchTimeDesc();

}
