package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.BadCredentialsException;
import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.model.Investment;
import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.User;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import com.locngo.loansystem.repository.InvestmentRepository;
import com.locngo.loansystem.request.Investment.CreateInvestmentRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.sercurity.otp.OtpGenerator;
import com.locngo.loansystem.service.InvestmentService;
import com.locngo.loansystem.service.LenderService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;

    private final LenderService lenderService;

    private final OtpGenerator otpGenerator;

    private final SmsService smsService;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 LenderService lenderService,
                                 OtpGenerator otpGenerator,
                                 SmsService smsService) {
        this.investmentRepository = investmentRepository;
        this.lenderService = lenderService;
        this.otpGenerator = otpGenerator;
        this.smsService = smsService;
    }

    @Override
    public void getOtpTransaction() {
        String otp = this.otpGenerator.generateOtpTransaction(this.lenderService.getCurrentLender().getUser().getUsername());
        smsService.sendSms(
                lenderService.getCurrentLender().getPhoneNumber(), "Transaction OTP Code: " + otp
        );
    }

    @Override
    public Investment createInvestment(CreateInvestmentRequest request) {

        Lender currentLender = lenderService.getCurrentLender();
        if (this.otpGenerator.validateOtp(currentLender.getUser().getUsername(), request.getOtp())) {
            Investment investment = new Investment();
            investment.setAmount(request.getAmount());
            investment.setDescription(request.getDescription());
            investment.setCreatedDate(LocalDateTime.now());
            investment.setCreatedBy(currentLender.getUser().getUsername());
            investment.setLender(lenderService.getCurrentLender());

            return investmentRepository.save(investment);
        }
        throw new BadCredentialsException("Invalid OTP Code!");
    }

    @Override
    public List<Investment> getAllInvestment() {
        return investmentRepository.findAll();
    }

    @Override
    public Investment getInvestment(Long id) {
        return investmentRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }
}
