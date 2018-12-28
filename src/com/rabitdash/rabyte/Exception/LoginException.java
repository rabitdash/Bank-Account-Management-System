package com.rabitdash.rabyte.Exception;

public class LoginException extends ATMException {
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
