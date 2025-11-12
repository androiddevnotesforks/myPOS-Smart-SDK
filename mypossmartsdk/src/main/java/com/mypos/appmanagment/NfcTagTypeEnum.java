package com.mypos.appmanagment;

public enum NfcTagTypeEnum {
    T2T(0),
    T4T(1);

    private final int value;

    private NfcTagTypeEnum(int var3) {
        this.value = var3;
    }

    public int getValue() {
        return this.value;
    }
}