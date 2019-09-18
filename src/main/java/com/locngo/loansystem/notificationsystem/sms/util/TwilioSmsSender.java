package com.locngo.loansystem.notificationsystem.sms.util;

import com.locngo.loansystem.errorhandling.error.BadCredentialsException;
import com.locngo.loansystem.notificationsystem.sms.request.SendSmsRequest;
import com.locngo.loansystem.notificationsystem.sms.property.TwilioConfiguration;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private TwilioConfiguration twilioConfiguration;

    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SendSmsRequest smsRequest) {
        try {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms: {}" + smsRequest);
        } catch (ApiException exception) {
            throw new BadCredentialsException(
                    "Phone number {" + smsRequest.getPhoneNumber() + "} is not a valid number"
            );
        }
    }
}
