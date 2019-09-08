package com.locngo.loansystem.notificationsystem.sms.util;

import com.locngo.loansystem.notificationsystem.sms.property.TwilioConfiguration;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    private TwilioConfiguration twilioConfiguration;

    public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthenToken()
        );
        LOGGER.info("Twilio Initialized ... with account sid {}", twilioConfiguration.getAccountSid());
    }
}
