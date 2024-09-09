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
    public static GameType fromValue(String value) {
        for (GameType gameType : GameType.values()) {
            if (gameType.toValue().equals(value)) {
                return gameType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
