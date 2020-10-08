package com.tripfellows.server.enums;

public enum RoleCodeEnum {
    DRIVER("DRIVER"),
    PASSENGER("PASSENGER");

    private final String value;

    RoleCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
