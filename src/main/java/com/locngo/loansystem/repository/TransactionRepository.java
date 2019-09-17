package com.locngo.loansystem.repository;

import com.locngo.loansystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getTransactionByPaidFalse();

    List<Transaction> getTransactionByPaidTrue();
}
