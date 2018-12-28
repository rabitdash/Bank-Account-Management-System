package com.rabitdash.rabyte.Accounts;

/**
 * @author rabitdash
 */

import com.rabitdash.rabyte.Exception.BalanceNotEnoughException;
import com.rabitdash.rabyte.Util.ACCOUNT_TYPE;

import java.io.Serializable;

public abstract class Account implements Serializable {

    public ACCOUNT_TYPE type;
    private long id;
    private String password;
    private String name;
    private String personId;
    private String email;
    private double balance;

    public Account() {
        balance = 0;
        id = 0;
    }

    public Account(long id, String password, String name, String personId, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.personId = personId;
        this.email = email;
        this.balance = 0;
    }

    public final Account deposit(double num) {
        this.balance += num;
        return this;
    }

    public abstract Account withdraw(double num) throws BalanceNotEnoughException;

    public ACCOUNT_TYPE getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return String.format("id:%d\nbalance:%f\npersonid:%s\ntype:%s\n", id, balance, personId, type);
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((Account) o).getId();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
