package com.locngo.loansystem.errorhandling.error;

public class EmailAlreadyExistedException extends RuntimeException {

    private static final long serialVersionUID = -6783059767252462481L;

    public EmailAlreadyExistedException(String email) {
        super("Email: {"  + email + "} was already registered!");
    }
}
