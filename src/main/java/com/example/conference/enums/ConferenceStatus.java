package com.example.conference.enums;

public enum ConferenceStatus {

    AVAILABLE("Available"),
    BUSY("Busy"),
    CANCELLED("Cancelled");

    private String value;

    ConferenceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
