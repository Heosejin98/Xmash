package com.tmp.xmash.dto.request;

import com.tmp.xmash.type.MatchType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GameModifyRequest (
        @Schema(description = "변경 승리팀")
        TeamRequest homeTeam,

        @Schema(description = "변경 패배팀")
        TeamRequest awayTeam,

        @Schema(description = "홈팀 점수", example = "15")
        int homeScore,

        @Schema(description = "원정팀 점수", example = "10")
        int awayScore,

        @Schema(description = "단식, 복식", example = "double")
        MatchType matchType
) {

    public List<String> homeUserIds() {
        return List.of(homeTeam.userId1(), homeTeam.userId2());
    }

    public List<String> awayUserIds() {
        return List.of(awayTeam.userId1(), awayTeam.userId2());
    }

}
