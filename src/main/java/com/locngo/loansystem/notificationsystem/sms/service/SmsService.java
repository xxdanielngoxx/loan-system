package com.locngo.loansystem.notificationsystem.sms.service;

import com.locngo.loansystem.notificationsystem.sms.request.SendSmsRequest;
import org.springframework.stereotype.Service;

@Service
public interface SmsService {
    void sendSms(SendSmsRequest smsRequest);
    void sendSms(String phoneNumber, String message);
}
