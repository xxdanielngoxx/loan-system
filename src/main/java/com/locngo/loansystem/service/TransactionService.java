package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Transaction;
import com.locngo.loansystem.request.transaction.CreateTransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {

    Transaction createTransaction(CreateTransactionRequest request);

    List<Transaction> getAllTransaction();

    Transaction getTransaction(Long id);

    List<Transaction> getPaidTransactions();

    List<Transaction> getUnPaidTransaction();

    Transaction updateTransactionIsPaid(Long id);

    Transaction updateTransactionIsPaid(Transaction transaction);
}
