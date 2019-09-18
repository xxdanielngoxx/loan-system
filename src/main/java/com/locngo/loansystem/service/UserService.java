package com.locngo.loansystem.service;

import com.locngo.loansystem.errorhandling.error.EmailAlreadyExistedException;
import com.locngo.loansystem.model.User;
import com.locngo.loansystem.request.jwt.Jwt;
import com.locngo.loansystem.request.user.AccessTokenRequest;
import com.locngo.loansystem.request.user.SigninRequest;
import com.locngo.loansystem.request.user.CreateUserRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void signin(SigninRequest request);

    Jwt getAccessToken(AccessTokenRequest accessTokenRequest);

    User create(CreateUserRequest request);

    String getCurrentLogin();

    User getUserByUsername(String username);
}
