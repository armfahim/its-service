package com.its.service.enums;

public enum Term {
    SEVEN("7"),
    FOURTEEN("14"),
    TWENTYONE("21"),
    TWENTYEIGHT("28"),
    THIRTY("30");

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
