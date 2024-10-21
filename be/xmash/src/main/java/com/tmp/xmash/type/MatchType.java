package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
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

    public static List<String> getMatchTypes() {
        return Arrays.stream(MatchType.values())
                .map(MatchType::name)
                .toList();
    }

}
