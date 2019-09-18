package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.BadCredentialsException;
import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.Withdrawal;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import com.locngo.loansystem.repository.WithdrawalRepository;
import com.locngo.loansystem.request.withdrawal.CreateWithdrawalRequest;
import com.locngo.loansystem.sercurity.otp.OtpGenerator;
import com.locngo.loansystem.service.LenderService;
import com.locngo.loansystem.service.WithdrawalService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;

    private final LenderService lenderService;

    private final OtpGenerator otpGenerator;

    private final SmsService smsService;

    public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository,
                                 LenderService lenderService,
                                 OtpGenerator otpGenerator,
                                 SmsService smsService) {
        this.withdrawalRepository = withdrawalRepository;
        this.lenderService = lenderService;
        this.otpGenerator = otpGenerator;
        this.smsService = smsService;
    }

    @Override
    public void getOtpTransaction() {
        String otp = otpGenerator.generateOtpTransaction(lenderService.getCurrentLender().getUser().getUsername());
        this.smsService.sendSms(
                lenderService.getCurrentLender().getPhoneNumber(), "Transaction OTP Code: " + otp
        );
    }

    @Override
    public Withdrawal createWithdrawal(CreateWithdrawalRequest request) {

        Lender currentLender = lenderService.getCurrentLender();
        if (this.otpGenerator.validateOtp(currentLender.getUser().getUsername(), request.getOtp())) {
            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setAmount(request.getAmount());
            withdrawal.setDescription(request.getDescription());
            withdrawal.setCreatedDate(LocalDateTime.now());
            withdrawal.setCreatedBy(lenderService.getCurrentLender().getUser().getUsername());
            withdrawal.setLender(currentLender);

            return this.withdrawalRepository.save(withdrawal);
        }
        throw new BadCredentialsException("Invalid OTP Code!");
    }

    @Override
    public List<Withdrawal> getAllWithdrawal() {
        return withdrawalRepository.findAll();
    }

    @Override
    public Withdrawal getWithdrawal(Long id) {
        return withdrawalRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }
}
