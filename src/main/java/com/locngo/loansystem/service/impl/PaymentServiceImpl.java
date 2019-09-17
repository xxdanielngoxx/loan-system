package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.errorhandling.error.TransactionWasPaidException;
import com.locngo.loansystem.model.BaseEntity;
import com.locngo.loansystem.model.Payment;
import com.locngo.loansystem.model.Transaction;
import com.locngo.loansystem.repository.PaymentRepository;
import com.locngo.loansystem.request.payment.CreateRepaymentRequest;
import com.locngo.loansystem.service.LenderService;
import com.locngo.loansystem.service.PaymentService;
import com.locngo.loansystem.service.TransactionService;
import com.locngo.loansystem.util.pagingandsorting.PageRequestBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final LenderService lenderService;

    private final TransactionService transactionService;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              LenderService lenderService,
                              @Lazy TransactionService transactionService) {
        this.paymentRepository = paymentRepository;
        this.lenderService = lenderService;
        this.transactionService = transactionService;
    }

    @Override
    public Payment createRepayment(CreateRepaymentRequest request) {

        Payment payment = new Payment();
        payment.setRepayment(true);

        Payment ownerPayment = this.getPayment(request.getPaymentId());
        payment.setTransaction(ownerPayment.getTransaction());
        payment.setOwnerRepayment(ownerPayment);

        payment.setAmount(request.getAmount());
        payment.setPaymentDate(request.getPaymentDate());

        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setCreatedBy(lenderService.getCurrentLender().getEmail());
        baseEntity.setCreatedDate(LocalDateTime.now());
        baseEntity.setModifiedBy(lenderService.getCurrentLender().getEmail());
        baseEntity.setModifiedDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> schedulerPayments(List<Payment> payments) {
        return paymentRepository.saveAll(payments);
    }

    @Override
    public Page<Payment> getAllPaymentWithPaging() {
        return paymentRepository.findAll((new PageRequestBuilder()).build());
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPayment(Long id) {
        return paymentRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<Payment> getPaidPaymentsByTransaction(Long transactionId) {
        Transaction transaction = transactionService.getTransaction(transactionId);
        return paymentRepository.getPaymentByTransactionAndPaidTrue(transaction);
    }

    @Override
    public List<Payment> getUnpaidInvalidPaymentByTransaction(Long transactionId) {
        Transaction transaction = transactionService.getTransaction(transactionId);
        return paymentRepository.getPaymentByPaidFalseAndTransactionAndPaymentDateIsBeforeOrderByPaymentDateAsc(transaction, LocalDateTime.now());
    }

    @Override
    public List<Payment> getUnpaidValidPaymentByTransaction(Long transactionId) {
        Transaction transaction = transactionService.getTransaction(transactionId);
        return paymentRepository.getPaymentByPaidFalseAndTransactionAndPaymentDateAfterOrderByPaymentDateDesc(transaction, LocalDateTime.now());
    }

    @Override
    public Payment updatePaidPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> updatePayments(List<Payment> payments) {
        return paymentRepository.saveAll(payments);
    }

    @Override
    public Payment markPaid(Long id) {
        Payment payment = this.getPayment(id);
        payment.setPaid(true);
        payment = this.updatePaidPayment(payment);
        if (paymentRepository.getPaymentByTransactionAndPaidFalse(payment.getTransaction()).isEmpty()) {
            Transaction transaction = payment.getTransaction();
            transaction.setPaid(true);
            transactionService.updateTransactionIsPaid(transaction);
        }
        return payment;
    }
}
