package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClubRole {
    @Schema(description = "동호회 회원")
    MEMBER,

    @Schema(description = "지속적으로 활동 중인 비회원")
    NON_MEMBER,

    @Schema(description = "임시 회원")
    GUEST;

    @JsonCreator
    public static ClubRole fromValue(String value) {
        return switch (value) {
            case "MEMBER" -> ClubRole.MEMBER;
            case "NON_MEMBER" -> ClubRole.NON_MEMBER;
            case "GUEST" -> ClubRole.GUEST;
            default -> throw new IllegalArgumentException("Unknown value " + value);
        };
    }
}
