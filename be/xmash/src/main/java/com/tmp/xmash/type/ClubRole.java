package com.tmp.xmash.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum ClubRole {
    @Schema(description = "동호회 회원")
    MEMBER,

    @Schema(description = "지속적으로 활동 중인 비회원")
    NON_MEMBER,

    @Schema(description = "임시 회원")
    GUEST
}
