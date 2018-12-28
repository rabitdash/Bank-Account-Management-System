package com.rabitdash.rabyte;

import com.rabitdash.rabyte.Accounts.*;
import com.rabitdash.rabyte.Exception.ATMException;
import com.rabitdash.rabyte.Exception.LoanException;
import com.rabitdash.rabyte.Exception.LoginException;
import com.rabitdash.rabyte.Exception.RegisterException;
import com.rabitdash.rabyte.Util.ACCOUNT_TYPE;

import java.util.ArrayList;
import java.util.List;

public class Bank {


    private final static long startIdNum = 0;
    private static volatile Bank instance = null;
    private static int nAccounts;// number of accounts
    private List<Account> accounts;

    private Bank() {
        accounts = new ArrayList<Account>();
        nAccounts = 0;
    }

    public static Bank getInstance() {
        if (instance == null) {
            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }

    Account getAccountById(long id) throws ATMException {
        for (Account a : accounts) {
            if (a.getId() == id) {
                return a;
            }
        }
        throw new ATMException("未找到账户");

    }

    public Account register(long id, String password, String name, String personId, String email, ACCOUNT_TYPE type) throws RegisterException {
        Account account;
        switch (type) {
            case SavingAccount:
                account = new SavingAccount(id, password, name, personId, email);
                break;
            case CreditAccount:
                account = new CreditAccount(id, password, name, personId, email);
                ((CreditAccount) account).setCeiling(100);
                break;
            case LoanCreditAccount:
                account = new LoanCreditAccount(id, password, name, personId, email);
                break;
            case LoanSavingAccount:
                account = new LoanSavingAccount(id, password, name, personId, email);
                break;
            default:
                throw new RegisterException("未知账户类型");
        }
        accounts.add(account);
        nAccounts++;
        return account;
    }

    public Account register(String password, String name, String personId, String email, ACCOUNT_TYPE type) throws RegisterException {
        Account account;
        long id = startIdNum + nAccounts;
        switch (type) {
            case SavingAccount:
                account = new SavingAccount(id, password, name, personId, email);
                break;
            case CreditAccount:
                account = new CreditAccount(id, password, name, personId, email);
                ((CreditAccount) account).setCeiling(100);
                break;
            case LoanCreditAccount:
                account = new LoanCreditAccount(id, password, name, personId, email);
                break;
            case LoanSavingAccount:
                account = new LoanSavingAccount(id, password, name, personId, email);
                break;
            default:
                throw new RegisterException("未知账户类型");
        }
        accounts.add(account);
        nAccounts++;
        return account;
    }

    public Account deposit(long id, double num) throws ATMException {
        return getAccountById(id).deposit(num);
    }

    public Account withdraw(long id, double num) throws ATMException {
        Account account;
        try {
            account = getAccountById(id).withdraw(num);

        } catch (ATMException e) {
            throw new ATMException(e.getMessage());
        }
        return account;
    }

    public Account requestLoan(long id, double num) throws LoanException, ATMException {
        Account account = getAccountById(id);
        if (account instanceof Loanable)
            ((Loanable) account).requestLoan(num);
        return account;
    }

    public Account payLoan(long id, double num) throws LoanException, ATMException {
        Account account = getAccountById(id);
        if (account instanceof Loanable)
            ((Loanable) account).payLoan(num);
        return account;
    }

    public Account setCeiling(long id, double num) throws ATMException {
        Account account = getAccountById(id);
        if (account instanceof CreditAccount) {
            ((CreditAccount) account).setCeiling(num);
//            System.out.println("fuck");
        }
        return account;
    }

    public Account transfer(long from, long to, double money) throws ATMException {
        try {
            withdraw(from, money);
        } catch (ATMException e) {
            e.printStackTrace();
        }
        try {
            deposit(to, money);
        } catch (ATMException e) {
            //把钱还回去
            deposit(from, money);
            e.printStackTrace();
        }
        return getAccountById(from);
    }

    public Account login(long id, String password) throws LoginException, ATMException {
        if (getAccountById(id).getPassword().equals(password))
            return getAccountById(id);
        throw new LoginException("账户名或密码错误");
    }

    public double allBalance() {
        double sumBalance = 0.0;
        for (Account i : accounts) {
            sumBalance += i.getBalance();

        }
        return sumBalance;
    }

    public double allCeiling() {
        double sumCeiling = 0.0;
        for (Account a : accounts) {
            if (a instanceof CreditAccount)
                sumCeiling += ((CreditAccount) a).getCeiling();
        }
        return sumCeiling;
    }

    public double allLoan() {
        double sumLoan = 0.0;
        for (Account a : accounts) {
            if (a instanceof Loanable)
                sumLoan += ((Loanable) a).getLoan();

        }
        return sumLoan;
    }


}
