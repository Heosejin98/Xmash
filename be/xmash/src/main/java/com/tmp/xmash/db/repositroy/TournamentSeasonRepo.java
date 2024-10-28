package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.TournamentSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentSeasonRepo extends JpaRepository<TournamentSeason, Long> {

    TournamentSeason findByCurrentSeasonTrue();

}
