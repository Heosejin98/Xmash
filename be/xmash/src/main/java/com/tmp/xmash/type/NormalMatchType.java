package com.tmp.xmash.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NormalMatchType {
    SINGLE,
    DOUBLE;

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase().replace('_', '-');
    }

    @JsonCreator
    public static NormalMatchType fromValue(String value) {
        for (NormalMatchType normalMatchType : NormalMatchType.values()) {
            if (normalMatchType.toValue().equals(value)) {
                return normalMatchType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}
