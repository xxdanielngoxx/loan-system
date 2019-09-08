package com.locngo.loansystem.errorhandling.error;

public class NameAlreadyExistedException extends RuntimeException {

    private static final long serialVersionUID = 7126914337961742478L;

    public NameAlreadyExistedException(String name) {
        super("Name {" + name + "} was already registered!");
    }
}
