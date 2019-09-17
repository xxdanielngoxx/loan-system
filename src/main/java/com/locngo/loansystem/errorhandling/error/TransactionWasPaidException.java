package com.locngo.loansystem.errorhandling.error;

public class TransactionWasPaidException extends RuntimeException {

    private static final long serialVersionUID = -8015070790396377745L;

    public TransactionWasPaidException() {
        super("Transaction was paid completely!");
    }
}
