package com.locngo.loansystem.errorhandling.error;

import java.util.concurrent.ExecutionException;

public class InvalidOtpException extends ExecutionException {

    private static final long serialVersionUID = 4273308946160271763L;

    public InvalidOtpException(String message) {
        super(message);
    }
}
