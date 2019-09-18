package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.BadCredentialsException;
import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.errorhandling.error.EmailAlreadyExistedException;
import com.locngo.loansystem.errorhandling.error.InvalidOtpException;
import com.locngo.loansystem.model.Status;
import com.locngo.loansystem.model.User;
import com.locngo.loansystem.notificationsystem.sms.request.SendSmsRequest;
import com.locngo.loansystem.notificationsystem.sms.service.SmsService;
import com.locngo.loansystem.repository.UserRepository;
import com.locngo.loansystem.request.jwt.Jwt;
import com.locngo.loansystem.request.user.AccessTokenRequest;
import com.locngo.loansystem.request.user.SigninRequest;
import com.locngo.loansystem.request.user.CreateUserRequest;
import com.locngo.loansystem.sercurity.JwtTokenProvider;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.sercurity.otp.OtpGenerator;
import com.locngo.loansystem.service.RoleService;
import com.locngo.loansystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final RoleService roleService;

    private final OtpGenerator otpGenerator;

    private final SmsService smsService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           AuthenticationManager authenticationManager,
                           OtpGenerator otpGenerator,
                           SmsService smsService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.otpGenerator = otpGenerator;
        this.smsService = smsService;
    }

    @Override
    public void signin(SigninRequest request) {
        try {
            System.out.printf("{User name: %s, password: %s}", request.getUsername(), request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Otp otp = Otp.of(request.getUsername(), otpGenerator.generateOtp(request.getUsername()));
            this.sendOtp(request.getUsername(), otp.getValue());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username password");
        }
    }

    @Override
    public Jwt getAccessToken(AccessTokenRequest accessTokenRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accessTokenRequest.getUsername(), accessTokenRequest.getPassword()));
            if (this.otpGenerator.validateOtp(accessTokenRequest.getUsername(), accessTokenRequest.getOtp())) {
                return jwtTokenProvider.createToken(accessTokenRequest.getUsername(), userRepository.findUserByUsername(accessTokenRequest.getUsername()).get().getRoles());
            }
            throw new BadCredentialsException("Invalid OTP code");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username password");
        }
    }

    @Override
    public User create(CreateUserRequest request) {
        if (!this.isUsernameAlreadyExisted(request.getUsername())) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setStatus(Status.ACTIVE);
            user.getRoles().add(roleService.getRoleByName("LENDER"));
            LOGGER.info("Role: {}", roleService.getRoleByName("LENDER"));

            user.setBaseEntity(request.getBaseEntity());
            return userRepository.save(user);
        }
        throw new EmailAlreadyExistedException(request.getUsername());
    }

    @Override
    public String getCurrentLogin() {
        return ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(DataNotFoundException::new);
    }

    private Boolean isUsernameAlreadyExisted(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    private void sendOtp(String username, String otp) {
        User currentUser = this.getUserByUsername(username);
        SendSmsRequest request = new SendSmsRequest(currentUser.getLender().getPhoneNumber(), "Verified code: " + otp);
        this.smsService.sendSms(request);
    }
}
