package com.netfocus.constants;

/**
 * Alarm severity constants.
 *
 * @author Asif Rahman
 *
 */
public enum Severity {
    FATAL(3), CRITICAL(2), WARNING(1), NORMAL(0);

    private final int value;

    private Severity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
