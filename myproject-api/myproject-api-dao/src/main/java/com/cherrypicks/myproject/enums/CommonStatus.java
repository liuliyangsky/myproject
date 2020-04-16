package com.cherrypicks.myproject.enums;

public enum CommonStatus {
    EXISTS(1), NOT_EXISTS(0), SUCCESS(1), FAILED(0), TRUE(1), FALSE(0);

    private int value;

    CommonStatus(final int value) {
        this.value = value;
    }

    public int toValue() {
        return this.value;
    }
}
