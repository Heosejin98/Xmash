package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("남"),
    FEMALE("여");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}