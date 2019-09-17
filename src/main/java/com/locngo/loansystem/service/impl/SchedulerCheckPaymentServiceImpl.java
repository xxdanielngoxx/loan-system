package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.model.Payment;
import com.locngo.loansystem.model.Transaction;
import com.locngo.loansystem.service.PaymentService;
import com.locngo.loansystem.service.SchedulerCheckPaymentService;
import com.locngo.loansystem.service.TransactionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchedulerCheckPaymentServiceImpl implements SchedulerCheckPaymentService {

    private static final LocalDateTime DEFAULT_NEXT_PAYMENT_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
            .plusDays(1)
            .plusHours(8);

    private final TransactionService transactionService;

    private final PaymentService paymentService;

    public SchedulerCheckPaymentServiceImpl(TransactionService transactionService, PaymentService paymentService) {
        this.transactionService = transactionService;
        this.paymentService = paymentService;
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Override
    public void updatePaymentDaily() {
        List<Transaction> unpaidTransactions = transactionService.getUnPaidTransaction();
        if (unpaidTransactions.isEmpty()) {
            return;
        }
        unpaidTransactions.forEach(this::scheduleUnpaidPayments);
    }

    private void scheduleUnpaidPayments(Transaction transaction) {
        LocalDateTime startedDate = DEFAULT_NEXT_PAYMENT_DATE;
        List<Payment> updatedPayments = new ArrayList<>();
        List<Payment> unpaidValidPayments = paymentService.getUnpaidValidPaymentByTransaction(transaction.getId());
        List<Payment> unpaidInvalidPayments = paymentService.getUnpaidInvalidPaymentByTransaction(transaction.getId());
        if(unpaidInvalidPayments.isEmpty()) {
            return;
        }
        if (!unpaidValidPayments.isEmpty()) {
            startedDate = unpaidValidPayments
                    .get(0).getPaymentDate()
                    .truncatedTo(ChronoUnit.DAYS)
                    .plusDays(1)
                    .plusHours(8);
        }

        for(Payment unpaidInvalidPayment: unpaidInvalidPayments) {
            Payment payment = new Payment();
            payment.setAmount(unpaidInvalidPayment.getAmount());
            payment.setPaid(false);
            payment.setBaseEntity(unpaidInvalidPayment.getBaseEntity());
            payment.setTransaction(unpaidInvalidPayment.getTransaction());
            payment.setDeleted(false);

            payment.setRepayment(true);
            payment.setOwnerRepayment(unpaidInvalidPayment);
            payment.setPaymentDate(startedDate);

            unpaidInvalidPayment.setDeleted(true);
            paymentService.updatePaidPayment(unpaidInvalidPayment);

            updatedPayments.add(payment);
            startedDate = startedDate.plusDays(1);
        }
        paymentService.updatePayments(updatedPayments);
    }
}
