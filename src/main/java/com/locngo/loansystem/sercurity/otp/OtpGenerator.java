package com.locngo.loansystem.sercurity.otp;

import com.locngo.loansystem.errorhandling.error.InvalidOtpException;
import org.springframework.stereotype.Service;

@Service
public interface OtpGenerator {
    String generateOtp(String key);
    String generateOtpTransaction(String key);
    Boolean validateOtp(String key, String otp);
}
