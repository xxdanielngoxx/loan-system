package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.Transaction;
import com.locngo.loansystem.request.transaction.CreateTransactionRequest;
import com.locngo.loansystem.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody CreateTransactionRequest request) {
        return this.transactionService.createTransaction(request);
    }

    @GetMapping
    public List<Transaction> getAllTransaction() {
        return this.transactionService.getAllTransaction();
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable("id") Long id) {
        return this.transactionService.getTransaction(id);
    }

    @GetMapping("/paid")
    public List<Transaction> getPaidTransactions() {
        return transactionService.getPaidTransactions();
    }

    @GetMapping("/unpaid")
    public List<Transaction> getUnPaidTransaction() {
        return transactionService.getUnPaidTransaction();
    }
}
