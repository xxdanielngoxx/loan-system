package com.locngo.loansystem.sercurity;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 2579647714823150158L;

    public InvalidJwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
