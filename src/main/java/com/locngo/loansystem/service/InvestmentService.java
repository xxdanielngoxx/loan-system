package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Investment;
import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.request.Investment.CreateInvestmentRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InvestmentService {

    void getOtpTransaction();

    Investment createInvestment(CreateInvestmentRequest request);

    List<Investment> getAllInvestment();

    Investment getInvestment(Long id);
}
