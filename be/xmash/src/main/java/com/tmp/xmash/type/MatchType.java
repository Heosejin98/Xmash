package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum MatchType {
    @Schema(description = "전체 조회 용", example = "ALL")
    ALL,

    @Schema(description = "단식", example = "single")
    SINGLE,

    @Schema(description = "복식", example = "double")
    DOUBLE;

    @JsonValue
    public String toResponse() {
        return this.name().toLowerCase();
    }
}
