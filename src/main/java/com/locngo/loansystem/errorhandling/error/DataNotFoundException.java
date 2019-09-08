package com.locngo.loansystem.errorhandling.error;

public class DataNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 8902032204384649298L;

    public DataNotFoundException() {
        super("Data not found exception!");
    }
}
