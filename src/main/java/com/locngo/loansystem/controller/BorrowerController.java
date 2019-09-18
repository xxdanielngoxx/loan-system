package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.Borrower;
import com.locngo.loansystem.request.borrower.BorrowerCreateRequest;
import com.locngo.loansystem.request.common.OtpRegisterRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.service.BorrowerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @PostMapping("/otp")
    @ResponseStatus(HttpStatus.OK)
    public void getOtpRegister(@RequestBody OtpRegisterRequest request) {
        this.borrowerService.getOtpRegister(request);
    }

    @PostMapping
    public Borrower createBorrower(@RequestBody BorrowerCreateRequest request) {
        return this.borrowerService.createBorrower(request);
    }

    @GetMapping
    public List<Borrower> getAllBorrower() {
        return this.borrowerService.getAllBorrower();
    }

    @GetMapping("/{id}")
    public Borrower getBorrower(@PathVariable("id") Long id) {
        return this.borrowerService.getBorrower(id);
    }
}
