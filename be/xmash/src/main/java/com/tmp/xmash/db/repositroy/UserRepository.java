package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUserId(String userId);

    List<AppUser> findByUserIdIn(List<String> userId);
}
