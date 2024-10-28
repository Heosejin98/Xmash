package com.tmp.xmash.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "TournamentGameResponse", description = "토너먼트 게임 응답 모델")
public record TournamentGameResponse(
        @Schema(name = "totalRound", example = "16", description = "32강, 16강 등")
        int totalRound,
        @Schema(name = "round", example = "8", description = "현재 round")
        int round,
        @Schema(name = "homeTeamId", example = "1", description = "현재 토너먼트의 홈팀 id")
        long homeTeamId,
        @Schema(name = "awayTeamId", example = "2", description = "현재 토너먼트의 어웨이팀 id")
        long awayTeamId,
        @Schema(name = "homeTeamPlayers", description = "홈팀 User 정보")
        List<PlayerResponse> homeTeamPlayers,
        @Schema(name = "awayTeamPlayers", description = "어웨이팀 User 정보")
        List<PlayerResponse> awayTeamPlayers,
        @Schema(name = "setGameResultResponses", description = "세트 게임 정보")
        List<SetGameResultResponse> setGameResultResponses,
        @Schema(name = "winnerId", example = "2", description = "setGameResultResponses 따른 승리자 id")
        long winnerId
)  {


}
