package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.errorhandling.error.EmailAlreadyExistedException;
import com.locngo.loansystem.errorhandling.error.IdentityCardAlreadyExistedException;
import com.locngo.loansystem.errorhandling.error.PhoneNumberAlreadyExistedException;
import com.locngo.loansystem.model.BaseEntity;
import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.User;
import com.locngo.loansystem.repository.LenderRepository;
import com.locngo.loansystem.request.lender.LenderCreateRequest;
import com.locngo.loansystem.request.user.CreateUserRequest;
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

    public LenderServiceImpl(LenderRepository lenderRepository,
                             UserService userService) {
        this.lenderRepository = lenderRepository;
        this.userService = userService;
    }

    @Override
    public Lender createLender(LenderCreateRequest request) {
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
}
