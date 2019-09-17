package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Payment;
import com.locngo.loansystem.model.Transaction;
import com.locngo.loansystem.request.payment.CreateRepaymentRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    Payment createRepayment(CreateRepaymentRequest request);

    List<Payment> schedulerPayments(List<Payment> payments);

    Page<Payment> getAllPaymentWithPaging();

    List<Payment> getAllPayment();

    Payment getPayment(Long id);

    List<Payment> getPaidPaymentsByTransaction(Long transactionId);

    List<Payment> getUnpaidInvalidPaymentByTransaction(Long transactionId);

    List<Payment> getUnpaidValidPaymentByTransaction(Long transactionId);

    Payment updatePaidPayment(Payment payment);

    List<Payment> updatePayments(List<Payment> payments);

    Payment markPaid(Long id);
}
