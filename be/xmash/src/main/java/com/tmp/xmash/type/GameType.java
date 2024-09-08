package com.tmp.xmash.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum GameType {
    NORMAL,
    RANK;

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
