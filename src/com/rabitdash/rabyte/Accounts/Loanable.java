package com.rabitdash.rabyte.Accounts;

import com.rabitdash.rabyte.Exception.LoanException;

public interface Loanable {
    Account requestLoan(double money) throws LoanException;

    Account payLoan(double money) throws LoanException;

    double getLoan();
}
