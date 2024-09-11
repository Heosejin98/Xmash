package com.tmp.xmash.service;

import static java.util.stream.Collectors.toList;

import com.tmp.xmash.db.repositroy.UserRankingRepository;
import com.tmp.xmash.dto.response.RankingResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RankingService {

    private final UserRankingRepository userRankingRepository;


    public List<RankingResponse> getRanking() {

        return userRankingRepository.findAllByOrderByRanking().stream()
                .map(RankingResponse::from)
                .collect(toList());
    }

}
