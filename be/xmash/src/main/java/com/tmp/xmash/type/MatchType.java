package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum MatchType {
    @Schema(description = "혼성 단식", example = "mixed-single")
    MIXED_SINGLE,

    @Schema(description = "남성 단식", example = "male-single")
    MALE_SINGLE,

    @Schema(description = "여성 단식", example = "female-single")
    FEMALE_SINGLE,

    @Schema(description = "혼성 복식", example = "mixed-double")
    MIXED_DOUBLE,

    @Schema(description = "남성 복식", example = "male-double")
    MALE_DOUBLE,

    @Schema(description = "여성 복식", example = "female-double")
    FEMALE_DOUBLE;


    @JsonValue
    public String toValue() {
        return this.name().toLowerCase().replace('_', '-');
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
