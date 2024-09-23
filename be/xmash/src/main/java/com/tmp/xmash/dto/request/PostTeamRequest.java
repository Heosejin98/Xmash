package com.tmp.xmash.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostTeamRequest(
        @Schema(description = "사용자 ID", example = "test")
        String myId,

        @Schema(description = "팀 사용자 ID", example = "test1")
        String teamId
) {
}
