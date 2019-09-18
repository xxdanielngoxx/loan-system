package com.locngo.loansystem.controller;

import com.locngo.loansystem.model.User;
import com.locngo.loansystem.request.jwt.Jwt;
import com.locngo.loansystem.request.user.AccessTokenRequest;
import com.locngo.loansystem.request.user.SigninRequest;
import com.locngo.loansystem.sercurity.otp.Otp;
import com.locngo.loansystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public void signin(@RequestBody SigninRequest request) {
        this.userService.signin(request);
    }

    @PostMapping("/access-token")
    public Jwt getAccessToken(@RequestBody AccessTokenRequest request) {
        return userService.getAccessToken(request);
    }
}
