package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.BadCredentialsException;
import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.model.Investment;
import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.User;
import com.locngo.loansystem.repository.InvestmentRepository;
import com.locngo.loansystem.request.Investment.CreateInvestmentRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.sercurity.otp.OtpGenerator;
import com.locngo.loansystem.service.InvestmentService;
import com.locngo.loansystem.service.LenderService;
import com.locngo.loansystem.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;

    private final LenderService lenderService;

    private final OtpGenerator otpGenerator;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 LenderService lenderService,
                                 OtpGenerator otpGenerator) {
        this.investmentRepository = investmentRepository;
        this.lenderService = lenderService;
        this.otpGenerator = otpGenerator;
    }

    @Override
    public Otp getOtpTransaction() {
        String otp = this.otpGenerator.generateOtpTransaction(this.lenderService.getCurrentLender().getUser().getUsername());
        return Otp.of("Transaction OTP Code", otp);
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
