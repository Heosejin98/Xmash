package com.tmp.xmash.dto.request;

import com.tmp.xmash.type.MatchType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;


@Schema(description = "게임 결과 요청 데이터")
public record GameResultRequest(
        @Schema(description = "홈팀 선수 id 리스트", example = "[\"test\", \"seheo\"]")
        List<String> homeTeam,

        @Schema(description = "원정팀 선수 id 리스트", example = "[\"yeye\", \"go\"]")
        List<String> awayTeam,

        @Schema(description = "홈팀 점수", example = "15")
        int homeScore,

        @Schema(description = "원정팀 점수", example = "10")
        int awayScore,

        @Schema(description = "단식, 복식", example = "double")
        MatchType matchType
) {

}