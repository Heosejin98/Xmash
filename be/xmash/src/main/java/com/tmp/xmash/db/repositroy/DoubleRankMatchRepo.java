package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.DoubleRankMatchHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoubleRankMatchRepo extends JpaRepository<DoubleRankMatchHistory, Long> {

    List<DoubleRankMatchHistory> findTop1000ByOrderByMatchTimeDesc();

}
