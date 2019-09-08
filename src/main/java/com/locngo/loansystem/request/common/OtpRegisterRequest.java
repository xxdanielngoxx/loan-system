package com.locngo.loansystem.request.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpRegisterRequest {

    @JsonProperty("phone_number")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
