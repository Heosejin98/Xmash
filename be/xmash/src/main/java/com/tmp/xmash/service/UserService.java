package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.exption.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public AppUser findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(BadRequestException::new);
    }

    @Transactional
    public List<AppUser> findByUserIdIn(List<String> userIds) {
        return userRepository.findByUserIdIn(userIds);
    }

}
