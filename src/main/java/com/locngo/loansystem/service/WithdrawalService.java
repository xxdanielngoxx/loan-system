package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.Withdrawal;
import com.locngo.loansystem.request.withdrawal.CreateWithdrawalRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface WithdrawalService {

    void getOtpTransaction();

    Withdrawal createWithdrawal(CreateWithdrawalRequest request);

    List<Withdrawal> getAllWithdrawal();

    Withdrawal getWithdrawal(Long id);
}
