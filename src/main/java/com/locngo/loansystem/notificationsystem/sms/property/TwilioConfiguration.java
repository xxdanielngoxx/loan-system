package com.locngo.loansystem.notificationsystem.sms.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfiguration {

    private String accountSid;

    private String authenToken;

    private String trialNumber;

    public TwilioConfiguration() {
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthenToken() {
        return authenToken;
    }

    public void setAuthenToken(String authenToken) {
        this.authenToken = authenToken;
    }

    public String getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(String trialNumber) {
        this.trialNumber = trialNumber;
    }
}
