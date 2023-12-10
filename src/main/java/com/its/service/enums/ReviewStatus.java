package com.its.service.enums;

public enum ReviewStatus {

    REVIEWED("REVIEWED"),
    CANCELLED("CANCELLED"),
    APPROVED("APPROVED");

    private final String label;

    ReviewStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
