package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.errorhandling.error.NameAlreadyExistedException;
import com.locngo.loansystem.model.BaseEntity;
import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.Loan;
import com.locngo.loansystem.repository.LoanRepository;
import com.locngo.loansystem.request.loan.LoanCreateRequest;
import com.locngo.loansystem.service.LenderService;
import com.locngo.loansystem.service.LoanService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    private final LenderService lenderService;

    public LoanServiceImpl(LoanRepository loanRepository,
                           LenderService lenderService) {
        this.loanRepository = loanRepository;
        this.lenderService = lenderService;
    }

    @Override
    public Loan createLoan(LoanCreateRequest request) {
        Lender currentLender = this.lenderService.getCurrentLender();

        Loan loan = new Loan();
        loan.setFund(request.getFund());
        loan.setPeriod(request.getPeriod());
        loan.setBaseRate(request.getBaseRate());

        if (this.isNameAlreadyExist(request.getName())) {
            throw new NameAlreadyExistedException(request.getName());
        }
        loan.setName(request.getName());

        loan.setDescription(request.getDescription());

        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setModifiedDate(LocalDateTime.now());
        baseEntity.setCreatedBy(currentLender.getUser().getUsername());
        baseEntity.setModifiedDate(LocalDateTime.now());
        baseEntity.setModifiedBy(currentLender.getUser().getUsername());
        loan.setBaseEntity(baseEntity);

        loan.setLender(currentLender);

        return this.loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoan() {
        return this.loanRepository.findAll();
    }

    @Override
    public Loan getLoan(Long id) {
        return this.loanRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private boolean isNameAlreadyExist(String name) {
        return this.loanRepository.existsByName(name);
    }
}
