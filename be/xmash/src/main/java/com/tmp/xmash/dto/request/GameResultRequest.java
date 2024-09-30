package com.tmp.xmash.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;


@Schema(description = "게임 결과 요청 데이터")
public record GameResultRequest(
        @Schema(description = "홈팀 선수 id 리스트", example = "[\"test\", \"test2\"]")
        List<String> homeTeam,

        @Schema(description = "원정팀 선수 id 리스트", example = "[\"test3\", \"test4\"]")
        List<String> awayTeam,

        @Schema(description = "홈팀 점수", example = "15")
        int homeScore,

        @Schema(description = "원정팀 점수", example = "10")
        int awayScore
) {

}