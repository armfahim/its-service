package com.its.service.enums;

public enum Month {
    January("1"),
    February("2"),
    March("3"),
    April("4"),
    May("5"),
    June("6"),
    July("7"),
    August("8"),
    September("9"),
    October("10"),
    November("11"),
    December("12");

    private final String displayName;

    Month(String displayName) {
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
