package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.*;
import com.locngo.loansystem.model.BaseEntity;
import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.User;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import com.locngo.loansystem.repository.LenderRepository;
import com.locngo.loansystem.request.common.OtpRegisterRequest;
import com.locngo.loansystem.request.lender.LenderCreateRequest;
import com.locngo.loansystem.request.user.CreateUserRequest;
import com.locngo.loansystem.sercurity.otp.OtpGenerator;
import com.locngo.loansystem.service.LenderService;
import com.locngo.loansystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class LenderServiceImpl implements LenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LenderServiceImpl.class);

    private final LenderRepository lenderRepository;

    private final UserService userService;

    private final OtpGenerator otpGenerator;

    private final SmsService smsService;

    public LenderServiceImpl(LenderRepository lenderRepository,
                             UserService userService,
                             OtpGenerator otpGenerator,
                             SmsService smsService) {
        this.lenderRepository = lenderRepository;
        this.userService = userService;
        this.otpGenerator = otpGenerator;
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
    public Lender createLender(LenderCreateRequest request) {
        this.validatePhoneNumber(request.getPhoneNumber(), request.getOtp());

        if (!this.isEmailAlreadyExisted(request.getEmail())) {
            Lender lender = new Lender();
            lender.setFirstName(request.getFirstName());
            lender.setLastName(request.getLastName());

            if (this.isIdentityCardAlreadyExisted(request.getIdentityCard())) {
                throw new IdentityCardAlreadyExistedException(request.getIdentityCard());
            }
            lender.setIdentityCard(request.getIdentityCard());

            if (this.isEmailAlreadyExisted(request.getEmail())) {
                throw new EmailAlreadyExistedException(request.getEmail());
            }
            lender.setEmail(request.getEmail());

            if (this.isPhoneNumberAlreadyExisted(request.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExistedException(request.getPhoneNumber());
            }
            lender.setPhoneNumber(request.getPhoneNumber());

            lender.setAddress(request.getAddress());

            BaseEntity baseEntity = new BaseEntity();
            baseEntity.setCreatedDate(LocalDateTime.now());
            baseEntity.setModifiedDate(LocalDateTime.now());

            lender.setBaseEntity(baseEntity);

            CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setUsername(request.getEmail());
            createUserRequest.setPassword(request.getPassword());
            createUserRequest.setBaseEntity(baseEntity);

            User user = userService.create(createUserRequest);

            lender.setUser(user);

            return this.lenderRepository.save(lender);
        }
        throw new EmailAlreadyExistedException(request.getEmail());
    }

    @Override
    public List<Lender> getAllLender() {
        LOGGER.info("Lender: {}", lenderRepository.findAll());
        return lenderRepository.findAll();
    }

    @Override
    public Lender getLender(Long id) {
        return lenderRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    @Override
    public Lender getCurrentLender() {
        String currentUser = userService.getCurrentLogin();
        User user = userService.getUserByUsername(currentUser);
        return lenderRepository.findByUser(user).orElseThrow(DataNotFoundException::new);
    }


    private Boolean isEmailAlreadyExisted(String email) {
        return this.lenderRepository.existsByEmailIgnoreCase(email);
    }

    private Boolean isPhoneNumberAlreadyExisted(String phoneNumber) {
        return this.lenderRepository.existsByPhoneNumber(phoneNumber);
    }

    private Boolean isIdentityCardAlreadyExisted(String identityCard) {
        return this.lenderRepository.existsByIdentityCardIgnoreCase(identityCard);
    }

    private boolean validatePhoneNumber(String phoneNumber, String otp) {
        if (this.otpGenerator.validateOtp(phoneNumber, otp)) {
            return true;
        }
        throw new BadCredentialsException("Invalid OTP Code!");
    }
}
