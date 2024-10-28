package com.tmp.xmash.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SetGameResultResponse(
        @Schema(name = "set", description = "현재 세트", example = "1")
        int set,
        @Schema(name = "setWinnerId", description = "현재 세트의 승리자 id", example = "1")
        int setWinnerId,
        @Schema(name = "homeTeamScore", description = "homeTeamScore", example = "21")
        int homeTeamScore,
        @Schema(name = "loserTeamScore", description = "loserTeamScore", example = "15")
        int loserTeamScore
) {
}
