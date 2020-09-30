package com.tripfellows.server.enums;

public enum TripStatusCodeEnum {
    WAITING("WAITING"),
    CANCELED("CANCELED"),
    STARTED("STARTED"),
    FINISHED("FINISHED");

    private final String value;

    TripStatusCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
