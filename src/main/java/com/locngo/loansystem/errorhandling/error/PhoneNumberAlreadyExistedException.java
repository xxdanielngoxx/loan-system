package com.locngo.loansystem.errorhandling.error;

public class PhoneNumberAlreadyExistedException extends RuntimeException{

    private static final long serialVersionUID = -4308052169485324869L;

    public PhoneNumberAlreadyExistedException(String phoneNumber) {
        super("Phone number {" + phoneNumber + "} was already registered!");
    }
}
