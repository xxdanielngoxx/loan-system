package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Loan;
import com.locngo.loansystem.request.loan.LoanCreateRequest;

import java.util.List;

public interface LoanService {
    Loan createLoan(LoanCreateRequest request);
    List<Loan> getAllLoan();
    Loan getLoan(Long id);
}
