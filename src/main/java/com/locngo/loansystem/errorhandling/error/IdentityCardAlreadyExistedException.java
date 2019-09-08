package com.locngo.loansystem.errorhandling.error;

public class IdentityCardAlreadyExistedException extends RuntimeException {

    public IdentityCardAlreadyExistedException(String identityCard) {
        super("Identity Card {" + identityCard + "} was already existed!");
    }
}
