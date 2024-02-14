package com.its.service.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Integer> getDisplayNameInList(){
        return Arrays.stream(Month.values())
                .map(month -> Integer.parseInt(month.getDisplayName()))
                .collect(Collectors.toList());
    }
}
