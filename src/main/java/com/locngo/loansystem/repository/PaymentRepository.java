package com.locngo.loansystem.repository;

import com.locngo.loansystem.model.Payment;
import com.locngo.loansystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> getPaymentByPaidFalseAndTransactionAndPaymentDateIsBeforeOrderByPaymentDateAsc(Transaction transaction, LocalDateTime now);

    List<Payment> getPaymentByPaidFalseAndTransactionAndPaymentDateAfterOrderByPaymentDateDesc(Transaction transaction, LocalDateTime now);

    List<Payment> getPaymentByTransactionAndPaidFalse(Transaction transaction);

    List<Payment> getPaymentByTransactionAndPaidTrue(Transaction transaction);
}
