package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.Investment;
import com.locngo.loansystem.request.Investment.CreateInvestmentRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.service.InvestmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping("/otp")
    @ResponseStatus(HttpStatus.OK)
    public void getOtpTransaction() {
        this.investmentService.getOtpTransaction();
    }

    @PostMapping
    public Investment createInvestment(@RequestBody CreateInvestmentRequest request) {
        return investmentService.createInvestment(request);
    }

    @GetMapping
    public List<Investment> getAllInvestment() {
        return investmentService.getAllInvestment();
    }

    @GetMapping("/{id}")
    public Investment getInvestmentById(@PathVariable("id") Long id) {
        return investmentService.getInvestment(id);
    }
}
