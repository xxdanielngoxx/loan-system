package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.Loan;
import com.locngo.loansystem.request.loan.LoanCreateRequest;
import com.locngo.loansystem.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public Loan createLoan(@RequestBody LoanCreateRequest request) {
        return this.loanService.createLoan(request);
    }

    @GetMapping
    public List<Loan> getAllLoan() {
        return this.loanService.getAllLoan();
    }

    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable("id") Long id) {
        return this.loanService.getLoan(id);
    }
}
