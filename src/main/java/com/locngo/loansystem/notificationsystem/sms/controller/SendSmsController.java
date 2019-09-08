package com.locngo.loansystem.notificationsystem.sms.controller;

import com.locngo.loansystem.notificationsystem.sms.request.SendSmsRequest;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/services/send-sms")
public class SendSmsController {

    private final SmsService smsService;

    public SendSmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/test")
    public void sendSms(@RequestBody SendSmsRequest sendSmsRequest) {
        this.smsService.sendSms(sendSmsRequest);
    }
}
