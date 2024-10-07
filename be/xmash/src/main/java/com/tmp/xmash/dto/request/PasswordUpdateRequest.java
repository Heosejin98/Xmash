package com.tmp.xmash.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PasswordUpdateRequest(
        @Schema(description = "기존 비밀번호", example = "qwe123")
        String prevPassword,

        @Schema(description = "변경 비밀번호", example = "qwer1234")
        String newPassword
) {
}
