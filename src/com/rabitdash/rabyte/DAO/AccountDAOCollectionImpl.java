package com.rabitdash.rabyte.DAO;

import com.rabitdash.rabyte.Accounts.*;
import com.rabitdash.rabyte.Util.ACCOUNT_TYPE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

//TODO
public class AccountDAOCollectionImpl implements AccountDAO {

    private Connection con = null;            //定义数据库连接对象
    private PreparedStatement state = null;    //当以数据库操作对象

    public AccountDAOCollectionImpl() {
    }

    public AccountDAOCollectionImpl(Connection con) {
        this.con = con;
    }

    @Override
    public boolean add(Account account) throws Exception {
        boolean flag = false;
        if (account != null) {
            String insertSql =
                    "insert into account(id,type,password,name,personId,email,balance,ceiling,loan) "
                            + " values(?,?,?,?,?,?,?,?,?)";
            this.state = this.con.prepareStatement(insertSql);


            this.state.setString(1, String.valueOf(account.getId()));
            this.state.setString(2, String.valueOf(account.getType().toString()));
            this.state.setString(3, String.valueOf(account.getPassword()));
            this.state.setString(4, String.valueOf(account.getName()));
            this.state.setString(5, String.valueOf(account.getPersonId()));
            this.state.setString(6, String.valueOf(account.getEmail()));
            this.state.setString(7, String.valueOf(account.getBalance()));
            if (account instanceof CreditAccount) {
                this.state.setString(8, String.valueOf(((CreditAccount) account).getCeiling()));
            } else {
                this.state.setString(8, "0");
            }
            if (account instanceof Loanable) {
                this.state.setString(9, String.valueOf(((Loanable) account).getLoan()));
            } else {
                this.state.setString(9, "0");
            }

            //成功插入数据
            if (this.state.executeUpdate() > 0) {
                flag = true;
            }
            this.state.close();
        }

        return flag;
    }

    @Override
    public boolean remove(Account account) throws Exception {
        boolean flag = false;
        String removeSql = " delete from account where id = ? ";
        this.state = this.con.prepareStatement(removeSql);
        this.state.setString(1, String.valueOf(account.getId()));

        if (this.state.executeUpdate() > 0) {        //删除成功

            flag = true;
        }

        this.state.close();        //关闭连接

        return flag;
    }

    @Override
    public Account search(long id) throws Exception {
        Account account = null;        //接受查询返回的对象
        ResultSet rs = null;        //接受查询结果
        ACCOUNT_TYPE accountType = null;
        //id不为空，且不为""

        //定义用于查询的sql语句
        String selectSql = "select id,type,password,name,personId,email,balance,ceiling,loan"
                + " from account where id=? ";

        this.state = this.con.prepareStatement(selectSql);
        this.state.setString(1, String.valueOf(id));
        rs = this.state.executeQuery();

        //查询成功
        //TODO account type 可能出问题
        if (rs.next()) {
            accountType = ACCOUNT_TYPE.valueOf(rs.getString("type"));
            switch (accountType) {
                case LoanCreditAccount:
                    account = new LoanCreditAccount();
                    break;
                case LoanSavingAccount:
                    account = new LoanSavingAccount();
                    break;
                case CreditAccount:
                    account = new CreditAccount();
                    break;
                case SavingAccount:
                    account = new SavingAccount();
                    break;
            }
            account.setId(Long.valueOf(rs.getString(1)));
            //TYPE不用set
            account.setPassword(String.valueOf(rs.getString(3)));
            account.setName(String.valueOf(rs.getString(4)));
            account.setPersonId(String.valueOf(rs.getString(5)));
            account.setEmail(String.valueOf(rs.getString(6)));
            account.setBalance(Double.valueOf(rs.getString(7)));
            if (account instanceof CreditAccount)
                ((CreditAccount) account).setCeiling(Double.valueOf(rs.getString(8)));
            if (account instanceof Loanable) {
                //不安全
                ((Loanable) account).requestLoan(Double.valueOf(rs.getString(9)));
            }

        }
        this.state.close();        //关闭连接
        return account;
    }


    @Override
    public Collection<Account> getAccounts() throws Exception {
        Collection<Account> list = new ArrayList<>();
        ResultSet rs = null;
        String selectSql = " select * from account ";
        this.state = this.con.prepareStatement(selectSql);
        rs = this.state.executeQuery();

        while (rs.next()) {
            ACCOUNT_TYPE accountType;
            Account account = null;
            accountType = ACCOUNT_TYPE.valueOf(rs.getString("type"));
            switch (accountType) {
                case LoanCreditAccount:
                    account = new LoanCreditAccount();
                    break;
                case LoanSavingAccount:
                    account = new LoanSavingAccount();
                    break;
                case CreditAccount:
                    account = new CreditAccount();
                    break;
                case SavingAccount:
                    account = new SavingAccount();
                    break;
            }
            account.setId(Long.valueOf(rs.getString(1)));
            //TYPE不用set
            account.setPassword(String.valueOf(rs.getString(3)));
            account.setName(String.valueOf(rs.getString(4)));
            account.setPersonId(String.valueOf(rs.getString(5)));
            account.setEmail(String.valueOf(rs.getString(6)));
            account.setBalance(Double.valueOf(rs.getString(7)));
            if (account instanceof CreditAccount)
                ((CreditAccount) account).setCeiling(Double.valueOf(rs.getString(8)));
            if (account instanceof Loanable) {
                //不安全
                ((Loanable) account).requestLoan(Double.valueOf(rs.getString(9)));
            }
            list.add(account);
        }
        return list;
    }

    @Override
    public boolean update(Account account) throws Exception {
        boolean flag = false;
        if (account != null) {
            String insertSql =
                    "update account where id = ? " +
                            "set " +
                            "type = ?, " +
                            "password= ?, " +
                            "name = ?, " +
                            "personId = ?, " +
                            "email = ?, " +
                            "balance = ?, " +
                            "ceiling = ?, " +
                            "loan = ?";
            this.state = this.con.prepareStatement(insertSql);


            this.state.setString(1, String.valueOf(account.getId()));
            this.state.setString(2, String.valueOf(account.getType().toString()));
            this.state.setString(3, String.valueOf(account.getPassword()));
            this.state.setString(4, String.valueOf(account.getName()));
            this.state.setString(5, String.valueOf(account.getPersonId()));
            this.state.setString(6, String.valueOf(account.getEmail()));
            this.state.setString(7, String.valueOf(account.getBalance()));
            if (account instanceof CreditAccount) {
                this.state.setString(8, String.valueOf(((CreditAccount) account).getCeiling()));
            } else {
                this.state.setString(8, "0");
            }
            if (account instanceof Loanable) {
                this.state.setString(9, String.valueOf(((Loanable) account).getLoan()));
            } else {
                this.state.setString(9, "0");
            }

            //成功插入数据
            if (this.state.executeUpdate() > 0) {
                flag = true;
            }
            this.state.close();
        }

        return flag;
    }

}
