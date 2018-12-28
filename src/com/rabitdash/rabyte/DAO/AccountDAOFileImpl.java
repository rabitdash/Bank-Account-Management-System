package com.rabitdash.rabyte.DAO;

import com.rabitdash.rabyte.Accounts.Account;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

//TODO
public class AccountDAOFileImpl implements AccountDAO {
    File file;
    HashMap<Long, Account> accountHashMap;

    public AccountDAOFileImpl() {

    }

    public AccountDAOFileImpl(String path) {
        file = new File(path);
    }

    public void writeFile() throws Exception {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(accountHashMap);
        outputStream.close();
    }

    public void readFile() throws Exception {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        accountHashMap = (HashMap<Long, Account>) inputStream.readObject();
        inputStream.close();

    }

    @Override
    public boolean add(Account account) throws Exception {
        readFile();
        accountHashMap.put(account.getId(), account);
        writeFile();
        return false;
    }

    @Override
    public boolean remove(Account account) throws Exception {
        readFile();
        accountHashMap.remove(account.getId());
        writeFile();
        return false;
    }

    @Override
    public Account search(long id) throws Exception {
        readFile();
        return accountHashMap.get(id);

    }

    @Override
    public Collection<Account> getAccounts() throws Exception {
        readFile();
        return accountHashMap.values();
    }

    @Override
    public boolean update(Account account) throws Exception {
        readFile();
        accountHashMap.put(account.getId(), account);
        writeFile();
        return false;
    }
}
