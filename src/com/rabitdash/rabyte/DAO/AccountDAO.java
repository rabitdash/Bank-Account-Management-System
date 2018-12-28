package com.rabitdash.rabyte.DAO;

import com.rabitdash.rabyte.Accounts.Account;

import java.util.Collection;

public interface AccountDAO {
    boolean add(Account account) throws Exception;

    boolean remove(Account account) throws Exception;

    Account search(long id) throws Exception;

    Collection<Account> getAccounts() throws Exception;

    boolean update(Account account) throws Exception;
}
