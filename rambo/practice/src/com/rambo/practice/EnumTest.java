package com.rambo.practice;

/**
 * Created by yuzhu on 12/12/18.
 */

public enum EnumTest {
    CONFIDENTIALITY,
    INTEGRITY,
    DEVICE_ROOT;

    public String value() {
        return name();
    }

    public static EnumTest fromValue(String v) {
        return valueOf(v);
    }
}


