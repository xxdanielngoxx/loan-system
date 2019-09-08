package com.locngo.loansystem.notificationsystem.sms.util;

import com.locngo.loansystem.notificationsystem.sms.request.SendSmsRequest;
import org.springframework.stereotype.Service;

@Service
public interface SmsSender {
    void sendSms(SendSmsRequest smsRequest);
}
