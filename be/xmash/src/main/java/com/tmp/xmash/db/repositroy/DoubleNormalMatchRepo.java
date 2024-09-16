package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.DoubleNormalMatchHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoubleNormalMatchRepo extends JpaRepository<DoubleNormalMatchHistory, Long> {

    List<DoubleNormalMatchHistory> findTop1000ByOrderByMatchTimeDesc();

}
