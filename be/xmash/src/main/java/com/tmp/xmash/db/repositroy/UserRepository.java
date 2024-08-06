package com.tmp.xmash.db.repositroy;

import com.tmp.xmash.db.entity.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserId(String username);
}
