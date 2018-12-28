package com.rabitdash.rabyte.Accounts;

import com.rabitdash.rabyte.Exception.BalanceNotEnoughException;
import com.rabitdash.rabyte.Util.ACCOUNT_TYPE;

//储蓄账户
public class SavingAccount extends Account {

    public SavingAccount() {
        super();
        type = ACCOUNT_TYPE.SavingAccount;
    }

    public SavingAccount(long id, String password, String name, String personId, String email) {
        super(id, password, name, personId, email);
        type = ACCOUNT_TYPE.SavingAccount;
    }

    @Override
    public Account withdraw(double num) throws BalanceNotEnoughException {
        //是否透支
        if (num > this.getBalance()) {
            throw new BalanceNotEnoughException("余额不足");
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
