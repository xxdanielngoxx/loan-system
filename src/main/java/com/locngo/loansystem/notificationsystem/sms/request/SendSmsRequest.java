package com.locngo.loansystem.notificationsystem.sms.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class SendSmsRequest {

    @NotBlank
    @JsonProperty("phone_number")
    private final String phoneNumber;

    @NotBlank
    private final String message;

    public SendSmsRequest(@JsonProperty("phone_number") String phoneNumber,
                          @JsonProperty("message") String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SendSmsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
