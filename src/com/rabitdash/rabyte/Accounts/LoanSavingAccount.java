package com.rabitdash.rabyte.Accounts;

import com.rabitdash.rabyte.Exception.BalanceNotEnoughException;
import com.rabitdash.rabyte.Exception.LoanException;
import com.rabitdash.rabyte.Util.ACCOUNT_TYPE;

import java.io.Serializable;

public class LoanSavingAccount extends SavingAccount implements Loanable, Serializable {
    private double loan;

    public LoanSavingAccount() {
        super();
        type = ACCOUNT_TYPE.LoanSavingAccount;
    }

    public LoanSavingAccount(long id, String password, String name, String personId, String email) {
        super(id, password, name, personId, email);
        type = ACCOUNT_TYPE.LoanSavingAccount;
    }

    @Override
    public Account requestLoan(double money) {
        this.loan += money;
        return this;
    }

    @Override
    public Account payLoan(double money) throws LoanException {
        try {
            this.withdraw(money);

        } catch (BalanceNotEnoughException e) {
            throw new LoanException(e.getMessage());
        }
        if (this.loan < money) {
            this.setBalance(money - this.loan);
            this.loan = 0.0;
        } else {
            this.loan -= money;
        }
        return this;
    }

    @Override
    public double getLoan() {
        return loan;
    }

    @Override
    public String toString() {
        return String.format("id:%d\nbalance:%f\npersonid:%s\ntype:%s\n", this.getId(), this.getBalance(), this.getPersonId(), type);
    }

}
