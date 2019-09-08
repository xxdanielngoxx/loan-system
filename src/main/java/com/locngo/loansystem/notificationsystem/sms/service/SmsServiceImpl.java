package com.locngo.loansystem.notificationsystem.sms.service;

import com.locngo.loansystem.notificationsystem.sms.request.SendSmsRequest;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import com.locngo.loansystem.notificationsystem.sms.util.SmsSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SmsServiceImpl implements SmsService {

    private final SmsSender smsSender;

    public SmsServiceImpl(@Qualifier("twilio") SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    @Override
    public void sendSms(SendSmsRequest smsRequest) {
        this.smsSender.sendSms(smsRequest);
    }
}
