package com.tmp.xmash.dto.response;

import com.tmp.xmash.db.entity.DoubleTournamentMatchHistory;

import io.swagger.v3.oas.annotations.media.Schema;

public record SetGameResultResponse(
        @Schema(name = "set", description = "현재 세트", example = "1")
        int set,
        @Schema(name = "setWinnerId", description = "현재 세트의 승리자 id", example = "1")
        long setWinnerId,
        @Schema(name = "winTeamScore", description = "winTeamScore", example = "21")
        int winTeamScore,
        @Schema(name = "loserTeamScore", description = "loserTeamScore", example = "15")
        int loserTeamScore
) {

        public static SetGameResultResponse from(DoubleTournamentMatchHistory tournamentMatchHistory) {
            return new SetGameResultResponse(
                tournamentMatchHistory.getMatchSet(),
                    tournamentMatchHistory.getWinTeam().getId(),
                    tournamentMatchHistory.getWinnerScore(), 
                    tournamentMatchHistory.getLoserScore()
            );
        }
}
