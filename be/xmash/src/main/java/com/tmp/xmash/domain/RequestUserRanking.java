package com.tmp.xmash.domain;

import com.tmp.xmash.db.entity.UserRanking;

import java.util.List;

public record RequestUserRanking(List<UserRanking> winnerRankings, List<UserRanking> loserRankings) {

}
