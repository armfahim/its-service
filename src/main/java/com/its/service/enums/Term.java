package com.its.service.enums;

public enum Term {
    SEVEN("7"),
    FIFTEEN("15"),
    TWENTYONE("21"),
    TWENTYEIGHT("28"),
    THIRTY("30"),
    COD("COD");

    private final String displayName;

    Term(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
