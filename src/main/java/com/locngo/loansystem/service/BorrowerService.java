package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Borrower;
import com.locngo.loansystem.request.borrower.BorrowerCreateRequest;
import com.locngo.loansystem.request.common.OtpRegisterRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowerService {

    Otp getOtpRegister(OtpRegisterRequest request);

    Borrower createBorrower(BorrowerCreateRequest request);

    List<Borrower> getAllBorrower();

    Borrower getBorrower(Long id);
}
