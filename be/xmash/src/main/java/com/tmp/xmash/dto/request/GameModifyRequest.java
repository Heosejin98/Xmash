package com.tmp.xmash.dto.request;

import com.tmp.xmash.type.MatchType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record GameModifyRequest (
        @Schema(description = "기존 승리 팀 id")
        TeamRequest prevWinnerTeam,

        @Schema(description = "기존 패배 팀 id ")
        TeamRequest prevLoserTeam,

        @Schema(description = "변경 승리팀")
        TeamRequest homeTeam,

        @Schema(description = "변경 패배팀")
        TeamRequest awayTeam,

        @Schema(description = "홈팀 점수", example = "15")
        int homeScore,

        @Schema(description = "원정팀 점수", example = "10")
        int awayScore,

        @Schema(description = "단식, 복식", example = "double")
        MatchType matchType,

        @Schema(description = "경기 시간", example = "2024-11-09 11:00:00")
        LocalDateTime matchTime
) {

    public List<String> prevWinnerUserIds() {
        return List.of(prevWinnerTeam.userId1(), prevWinnerTeam.userId2());
    }

    public List<String> prevLoserUserIds() {
        return List.of(prevLoserTeam.userId1(), prevLoserTeam.userId2());
    }

    public List<String> homeUserIds() {
        return List.of(homeTeam.userId1(), homeTeam.userId2());
    }

    public List<String> awayUserIds() {
        return List.of(awayTeam.userId1(), awayTeam.userId2());
    }

}
