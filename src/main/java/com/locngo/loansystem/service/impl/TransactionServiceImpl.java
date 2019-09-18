package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.BadCredentialsException;
import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.model.*;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import com.locngo.loansystem.repository.TransactionRepository;
import com.locngo.loansystem.request.transaction.CreateTransactionRequest;
import com.locngo.loansystem.sercurity.otp.OtpGenerator;
import com.locngo.loansystem.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final LoanService loanService;

    private final BorrowerService borrowerService;

    private final LenderService lenderService;

    private final PaymentService paymentService;

    private final TransactionRepository transactionRepository;

    private final OtpGenerator otpGenerator;

    private final SmsService smsService;

    public TransactionServiceImpl(LoanService loanService,
                                  BorrowerService borrowerService,
                                  LenderService lenderService,
                                  PaymentService paymentService,
                                  TransactionRepository transactionRepository,
                                  OtpGenerator otpGenerator,
                                  SmsService smsService) {
        this.loanService = loanService;
        this.borrowerService = borrowerService;
        this.lenderService = lenderService;
        this.paymentService = paymentService;
        this.transactionRepository = transactionRepository;
        this.otpGenerator = otpGenerator;
        this.smsService = smsService;
    }

    @Override
    public void getOtpTransaction() {
        String otp = this.otpGenerator.generateOtpTransaction(lenderService.getCurrentLender().getUser().getUsername());
        LOGGER.info("OTP Transaction Code Provide: {}" + otp);
        this.smsService.sendSms(lenderService.getCurrentLender().getPhoneNumber(), "Transaction OTP Code: " + otp);
    }

    @Override
    public Transaction createTransaction(CreateTransactionRequest request) {
        LOGGER.info("OTP Transaction Code Request: {}" + request.getOtp());
        if (this.otpGenerator.validateOtp(this.lenderService.getCurrentLender().getUser().getUsername(), request.getOtp())) {
            Transaction transaction = new Transaction();

            Loan loan = loanService.getLoan(request.getLoanId());
            transaction.setLoan(loan);

            Borrower borrower = borrowerService.getBorrower(request.getBorrowerId());
            transaction.setBorrower(borrower);

            transaction.setDescription(request.getDescription());

            transaction.setCreatedBy(lenderService.getCurrentLender().getEmail());
            transaction.setCreatedDate(LocalDateTime.now());

            transaction.setStartedDate(LocalDateTime.now()
                    .truncatedTo(ChronoUnit.DAYS)
                    .plusDays(1).truncatedTo(ChronoUnit.DAYS)
                    .plusHours(8));

            Transaction createdTransaction = transactionRepository.save(transaction);

            List<Payment> scheduledPayments = this.paymentService.schedulerPayments(
                    this.schedulePayments(createdTransaction)
            );

            scheduledPayments.forEach(payment -> createdTransaction.getPayments().add(payment));

            LOGGER.info("Transaction: {}", transaction);

            return transactionRepository.save(createdTransaction);
        }
        throw new BadCredentialsException("Invalid OTP Code!");
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<Transaction> getPaidTransactions() {
        return transactionRepository.getTransactionByPaidTrue();
    }

    @Override
    public List<Transaction> getUnPaidTransaction() {
        return transactionRepository.getTransactionByPaidFalse();
    }

    @Override
    public Transaction updateTransactionIsPaid(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
        transaction.setPaid(true);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransactionIsPaid(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    private List<Payment> schedulePayments(Transaction transaction) {
        List<Payment> payments = new ArrayList<>();
        Double amountEachDay = this.getAmountEachDay(transaction);
        LocalDateTime startedDate = transaction.getStartedDate();

        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setModifiedBy(transaction.getCreatedBy());
        baseEntity.setCreatedBy(transaction.getCreatedBy());
        baseEntity.setCreatedDate(transaction.getCreatedDate());
        baseEntity.setModifiedDate(transaction.getCreatedDate());

        for (int i = 0; i < transaction.getLoan().getPeriod(); i++, startedDate = startedDate.plusDays(1)) {
            Payment payment = new Payment();

            payment.setTransaction(transaction);
            payment.setAmount(amountEachDay);
            payment.setPaymentDate(startedDate);

            payment.setBaseEntity(baseEntity);
            payments.add(payment);
        }
        return payments;
    }

    private Double getAmountEachDay(Transaction transaction) {
        Loan loan = transaction.getLoan();
        return loan.getFund() * (1 + loan.getBaseRate()) / loan.getPeriod();
    }
}
