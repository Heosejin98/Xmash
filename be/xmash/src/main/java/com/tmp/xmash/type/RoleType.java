package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType {
    @Schema(description = "동호회 회원")
    MEMBER("member"),

    @Schema(description = "지속적으로 활동 중인 비회원")
    NON_MEMBER("non_member"),

    @Schema(description = "임시 회원")
    GUEST("guest");

    private final String role;

    @JsonCreator
    public static RoleType fromValue(String value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.name().equals(value)) {
                return roleType;
            }
        }
        throw new IllegalArgumentException("Unknown value" + value);
    }
}
