package com.tmp.xmash.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TeamRequest(
        @Schema(description = "user id 1", example = "seheo")
        String userId1,

        @Schema(description = "user id 2", example = "test", nullable = true)
        String userId2
) {

}
