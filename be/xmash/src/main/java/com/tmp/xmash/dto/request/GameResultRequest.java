package com.tmp.xmash.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record GameResultRequest(Integer homeScore, Integer awayScore, String awayPlayerId) {


    /**
     * 내 점수
     *
     * @return homeScore
     */
    @Override
    @Schema(name = "homeScore", example = "15", description = "내 점수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("homeScore")
    public Integer homeScore() {
        return homeScore;
    }

    /**
     * 상대 점수
     *
     * @return opponentScore
     */
    @Override
    @Schema(name = "awayScore", example = "10", description = "상대 점수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("awayScore")
    public Integer awayScore() {
        return awayScore;
    }


    /**
     * 상대 플레이어 ID
     *
     * @return opponentPlayerId
     */
    @Override
    @Schema(name = "awayPlayerId", example = "player123", description = "상대 플레이어 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("awayPlayerId")
    public String awayPlayerId() {
        return awayPlayerId;
    }
}
