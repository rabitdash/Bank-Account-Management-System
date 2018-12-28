package com.rabitdash.rabyte.Exception;

public class BalanceNotEnoughException extends ATMException {
    public BalanceNotEnoughException() {
        super();
    }

    public BalanceNotEnoughException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
