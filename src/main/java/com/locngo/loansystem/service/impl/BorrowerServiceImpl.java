package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.*;
import com.locngo.loansystem.model.Address;
import com.locngo.loansystem.model.BaseEntity;
import com.locngo.loansystem.model.Borrower;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import com.locngo.loansystem.repository.BorrowerRepository;
import com.locngo.loansystem.request.borrower.BorrowerCreateRequest;
import com.locngo.loansystem.request.common.OtpRegisterRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.sercurity.otp.OtpGenerator;
import com.locngo.loansystem.service.BorrowerService;
import com.locngo.loansystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BorrowerServiceImpl implements BorrowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowerServiceImpl.class);

    private final BorrowerRepository borrowerRepository;

    private final OtpGenerator otpGenerator;

    private final UserService userService;

    private final SmsService smsService;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository,
                               OtpGenerator otpGenerator,
                               UserService userService,
                               SmsService smsService) {
        this.borrowerRepository = borrowerRepository;
        this.otpGenerator = otpGenerator;
        this.userService = userService;
        this.smsService = smsService;
    }

    @Override
    public void getOtpRegister(OtpRegisterRequest request) {
        if (this.isPhoneNumberAlreadyExisted(request.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistedException(request.getPhoneNumber());
        }
        String otp = otpGenerator.generateOtp(request.getPhoneNumber());
        this.smsService.sendSms(request.getPhoneNumber(), "Verified Code: " + otp);
    }

    @Override
    public Borrower createBorrower(BorrowerCreateRequest request) {
        if (this.otpGenerator.validateOtp(request.getPhoneNumber(), request.getOtp())) {
            Borrower borrower = new Borrower();
            this.setEmail(borrower, request.getEmail());
            this.setPhoneNumber(borrower, request.getPhoneNumber());
            this.setIdentityCard(borrower, request.getIdentityCard());
            borrower.setFirstName(request.getFirstName());
            borrower.setLastName(request.getLastName());

            Address address = new Address();
            address.setProvince(request.getAddress().getProvince());
            address.setDistrict(request.getAddress().getDistrict());
            address.setWards(request.getAddress().getWards());
            address.setStreetAddress(request.getAddress().getStreetAddress());
            borrower.setAddress(address);

            BaseEntity baseEntity = new BaseEntity();
            baseEntity.setCreatedDate(LocalDateTime.now());
            baseEntity.setCreatedBy(userService.getCurrentLogin());
            baseEntity.setModifiedDate(LocalDateTime.now());
            baseEntity.setModifiedBy(userService.getCurrentLogin());
            borrower.setBaseEntity(baseEntity);

            return this.borrowerRepository.save(borrower);
        }
        throw new BadCredentialsException("Invalid OTP {" +request.getOtp() + "} Code");
    }

    @Override
    public List<Borrower> getAllBorrower() {
        return this.borrowerRepository.findAll();
    }

    @Override
    public Borrower getBorrower(Long id) {
        return borrowerRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private Boolean isEmailAlreadyExisted(String email) {
        return this.borrowerRepository.existsByEmailIgnoreCase(email);
    }

    private Boolean isPhoneNumberAlreadyExisted(String phoneNumber) {
        return this.borrowerRepository.existsByPhoneNumber(phoneNumber);
    }

    private Boolean isIdentityCardAlreadyExisted(String identityCard) {
        return this.borrowerRepository.existsByIdentityCardIgnoreCase(identityCard);
    }

    private void setEmail(Borrower borrower, String email) {
        if (!this.isEmailAlreadyExisted(email)) {
            borrower.setEmail(email);
            return;
        }
        throw new EmailAlreadyExistedException(email);
    }

    private void setPhoneNumber(Borrower borrower, String phoneNumber) {
        if (!this.isPhoneNumberAlreadyExisted(phoneNumber)) {
            borrower.setPhoneNumber(phoneNumber);
            return;
        }
        throw new  EmailAlreadyExistedException(phoneNumber);
    }

    private void setIdentityCard(Borrower borrower, String identityCard) {
        if (!this.isIdentityCardAlreadyExisted(identityCard)) {
            borrower.setIdentityCard(identityCard);
            return;
        }
        throw new IdentityCardAlreadyExistedException(identityCard);
    }

}
