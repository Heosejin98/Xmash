package com.tmp.xmash.dto.response;

public record SetGameResultResponse(
        int set,
        int setWinnerId,
        int homeTeamScore,
        int loserTeamScore
) {
}
