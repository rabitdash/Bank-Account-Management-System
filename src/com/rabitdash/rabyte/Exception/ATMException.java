package com.rabitdash.rabyte.Exception;

import javax.swing.*;

public class ATMException extends Exception {
    public ATMException() {
        super();
    }

    public ATMException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        JOptionPane.showMessageDialog(null, this.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
    }

}