package com.locngo.loansystem.sercurity.otp;

public class Otp {

    private String key;

    private String value;

    private Otp(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static Otp of(String key, String value) {
        return new Otp(key, value);
    }
}
