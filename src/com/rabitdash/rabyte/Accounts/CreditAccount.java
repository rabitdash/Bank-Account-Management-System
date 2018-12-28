package com.rabitdash.rabyte.Accounts;

import com.rabitdash.rabyte.Exception.BalanceNotEnoughException;
import com.rabitdash.rabyte.Util.ACCOUNT_TYPE;

public class CreditAccount extends Account {
    protected double ceiling;

    public CreditAccount() {
        super();
        this.ceiling = 0;
        type = ACCOUNT_TYPE.CreditAccount;
    }

    public CreditAccount(long id, String password, String name, String personId, String email) {
        super(id, password, name, personId, email);
        type = ACCOUNT_TYPE.CreditAccount;
    }

    public double getCeiling() {
        return ceiling;
    }

    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }

    @Override
    public Account withdraw(double num) throws BalanceNotEnoughException {
        //是否透支
        if (num > this.getBalance() + this.getCeiling()) {
            throw new BalanceNotEnoughException("透支余额不足");
        } else if (num > this.getBalance()) {
            this.setCeiling(this.getCeiling() + this.getBalance() - num);
            this.setBalance(0);
            return this;
        } else {
            this.setBalance(this.getBalance() - num);
            return this;
        }
    }

    @Override
    public String toString() {
        return String.format("id:%d\nbalance:%f\npersonid:%s\ntype:%s\n", this.getId(), this.getBalance(), this.getPersonId(), type);
    }

}
