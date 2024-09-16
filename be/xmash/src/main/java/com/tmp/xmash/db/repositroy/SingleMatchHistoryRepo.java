package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.SingleNormalMatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleMatchHistoryRepo extends JpaRepository<SingleNormalMatchHistory, Long> {

    List<SingleNormalMatchHistory> findTop1000ByOrderByMatchTimeDesc();

}
