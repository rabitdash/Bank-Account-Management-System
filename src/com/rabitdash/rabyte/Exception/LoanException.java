package com.rabitdash.rabyte.Exception;

public class LoanException extends ATMException {
    public LoanException() {
        super();
    }

    public LoanException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}