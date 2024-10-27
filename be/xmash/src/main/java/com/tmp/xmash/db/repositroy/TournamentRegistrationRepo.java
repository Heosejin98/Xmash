package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.TournamentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRegistrationRepo extends JpaRepository<TournamentRegistration, Long> {

}
