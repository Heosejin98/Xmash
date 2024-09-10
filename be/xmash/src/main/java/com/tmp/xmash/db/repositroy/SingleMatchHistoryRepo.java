package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.SingleMatchHistory;
import com.tmp.xmash.type.MatchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleMatchHistoryRepo extends JpaRepository<SingleMatchHistory, Long> {

    List<SingleMatchHistory> findTop1000ByMatchTypeOrderByMatchTimeDesc(MatchType matchType);

}
