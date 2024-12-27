package com.da.b24tm;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Operation {
    OPENED("open"),
    PAUSED("pause"),
    CLOSED("close"),
    STATUS("status"),
    AUTO("auto"),
    EXIT("exit");

    private final String value;

    Operation(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Operation fromString(String str) {
        try {
            return Operation.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Operation name: " + str);
        }
    }
}
