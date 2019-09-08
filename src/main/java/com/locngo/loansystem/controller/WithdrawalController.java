package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.Withdrawal;
import com.locngo.loansystem.request.withdrawal.CreateWithdrawalRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.service.WithdrawalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/withdrawals")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @GetMapping("/otp")
    public Otp getOtpTransaction() {
        return this.withdrawalService.getOtpTransaction();
    }

    @PostMapping
    public Withdrawal createWithdrawal(@RequestBody CreateWithdrawalRequest request) {
        return this.withdrawalService.createWithdrawal(request);
    }

    @GetMapping
    public List<Withdrawal> getAllWithdrawal() {
        return this.withdrawalService.getAllWithdrawal();
    }

    @GetMapping("/{id}")
    public Withdrawal getWithdrawal(@PathVariable("id") Long id) {
        return this.withdrawalService.getWithdrawal(id);
    }
}
