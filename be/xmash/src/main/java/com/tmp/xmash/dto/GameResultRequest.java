package com.tmp.xmash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameResultRequest {


    private Integer homeScore;

    private Integer awayScore;

    private String awayPlayerId;


    /**
     * 내 점수
     * @return homeScore
     */
    @Schema(name = "homeScore", example = "15", description = "내 점수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("homeScore")
    public Integer getHomeScore() {
        return homeScore;
    }

    /**
     * 상대 점수
     * @return opponentScore
     */
    @Schema(name = "awayScore", example = "10", description = "상대 점수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("awayScore")
    public Integer getAwayScore() {
        return awayScore;
    }


    /**
     * 상대 플레이어 ID
     * @return opponentPlayerId
     */
    @Schema(name = "awayPlayerId", example = "player123", description = "상대 플레이어 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("awayPlayerId")
    public String getAwayPlayerId() {
        return awayPlayerId;
    }
}
