package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.SingleMatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleMatchHistoryRepo extends JpaRepository<SingleMatchHistory, Long> {

}
