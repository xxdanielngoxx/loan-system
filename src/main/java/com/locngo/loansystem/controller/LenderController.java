package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.request.common.OtpRegisterRequest;
import com.locngo.loansystem.request.lender.LenderCreateRequest;
import com.locngo.loansystem.service.LenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/lenders")
public class LenderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderController.class);

    private final LenderService lenderService;

    public LenderController(LenderService lenderService) {
        this.lenderService = lenderService;
    }

    @PostMapping("/otp")
    @ResponseStatus(HttpStatus.OK)
    public void getOtpTransaction(@RequestBody OtpRegisterRequest request) {
        this.lenderService.getOtpRegister(request);
    }

    @PostMapping("signup")
    public Lender createLender(@RequestBody LenderCreateRequest request) {
        return this.lenderService.createLender(request);
    }

    @GetMapping
    public List<Lender> getAllLender() {
        return this.lenderService.getAllLender();
    }

    @GetMapping("/{id}")
    public Lender getLender(@PathVariable("id") Long id) {
        return this.lenderService.getLender(id);
    }
}
