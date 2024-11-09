package com.tmp.xmash.service;

import com.tmp.xmash.db.entity.AppUser;
import com.tmp.xmash.db.repositroy.UserRepository;
import com.tmp.xmash.exption.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public AppUser findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(BadRequestException::new);
    }

    @Transactional(readOnly = true)
    public List<AppUser> findByUserIdIn(List<String> userIds) {
        return userRepository.findByUserIdIn(userIds);
    }


    @Transactional(readOnly = true)
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

}
