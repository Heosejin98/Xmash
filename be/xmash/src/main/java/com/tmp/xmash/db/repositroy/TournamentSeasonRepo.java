package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.TournamentSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentSeasonRepo extends JpaRepository<TournamentSeason, Long> {

    TournamentSeason findByCurrentSeasonTrue();

    @Query("SELECT DISTINCT ts FROM TournamentSeason ts " +
           "LEFT JOIN FETCH ts.doubleTournamentMatchResults dmr " +
           "LEFT JOIN FETCH dmr.homeTeam ht " +
           "LEFT JOIN FETCH dmr.awayTeam at " +
           "LEFT JOIN FETCH ht.user1 hu1 " +
           "LEFT JOIN FETCH ht.user2 hu2 " +
           "LEFT JOIN FETCH at.user1 au1 " +
           "LEFT JOIN FETCH at.user2 au2 " +
           "WHERE ts.season = :season")
    Optional<TournamentSeason> findBySeasonWithMatchResults(int season);

}
