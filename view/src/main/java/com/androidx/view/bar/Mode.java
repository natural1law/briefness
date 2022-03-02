package com.androidx.view.bar;

public enum Mode {

    AUTO(-1),
    SELECTED(0),
    LABELED(1),
    UNLABELED(2);

    private final int value;

    Mode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
