package com.locngo.loansystem.sercurity.otp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.locngo.loansystem.errorhandling.error.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class OtpGeneratorImpl implements OtpGenerator {

    private static final Integer EXPIRE_MIN = 1;

    private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final Integer OTP_TRANSACTION_LENGTH = 10;

    private LoadingCache<String, String> otpCache;

    public OtpGeneratorImpl() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) {
                        if (s != null && !s.equals("")) {
                            return s;
                        } else {
                            throw new BadCredentialsException("Please login!");
                        }
                    }
                });
    }

    @Override
    public String generateOtp(String key) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, String.valueOf(otp));
        return String.valueOf(otp);
    }

    @Override
    public String generateOtpTransaction(String key) {
        String otpTransaction = this.generateOtpTransaction(OTP_TRANSACTION_LENGTH);
        otpCache.put(key, otpTransaction);
        return otpTransaction;
    }

    @Override
    public Boolean validateOtp(String key, String otp) {
        if(this.getOtpByKey(key) != null && this.getOtpByKey(key).equals(otp)) {
            this.clearOtpFromCache(key);
            return true;
        }
        this.clearOtpFromCache(key);
        return false;
    }

    public static OtpGeneratorImpl of() {
        return new OtpGeneratorImpl();
    }

    private String getOtpByKey(String key)  {
        try {
            return otpCache.get(key);
        } catch (ExecutionException e) {
            throw new BadCredentialsException("Expired or invalid OTP code!");
        }
    }

    private void clearOtpFromCache(String key) {
        otpCache.invalidate(key);
    }

    private String generateOtpTransaction(int length) {
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int position = (int) (Math.random()*ALPHA_NUMERIC.length());
            builder.append(ALPHA_NUMERIC.charAt(position));
        }
        return builder.toString();
    }
}
