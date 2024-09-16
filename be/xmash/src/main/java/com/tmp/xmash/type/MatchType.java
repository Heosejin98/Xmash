package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum MatchType {
    @Schema(description = "단식", example = "single")
    SINGLE,

    @Schema(description = "복식", example = "double")
    DOUBLE;

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static MatchType fromValue(String value) {
        for (MatchType matchType : MatchType.values()) {
            if (matchType.toValue().equals(value)) {
                return matchType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}
