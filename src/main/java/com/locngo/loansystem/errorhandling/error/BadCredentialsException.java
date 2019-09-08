package com.locngo.loansystem.errorhandling.error;

public class BadCredentialsException extends RuntimeException{

    private static final long serialVersionUID = -2011754862729993033L;

    public BadCredentialsException(String message) {
        super(message);
    }
}
