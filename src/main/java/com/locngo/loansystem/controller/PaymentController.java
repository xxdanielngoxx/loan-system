package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.Payment;
import com.locngo.loansystem.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAllPayment() {
        return this.paymentService.getAllPaymentWithPaging().getContent();
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable("id") Long id) {
        return this.paymentService.getPayment(id);
    }
}
