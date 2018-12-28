package com.rabitdash.rabyte.DAO;

import java.sql.Connection;

public class DAOFactory {
    public static AccountDAO getArrayDAO(Connection con) {
        return new AccountDAOArrayImpl(con);
    }

    public static AccountDAO getCollectionDAO(Connection con) {
        return new AccountDAOCollectionImpl(con);
    }

    public static AccountDAO getFileDAO(String path) {
        return new AccountDAOFileImpl(path);
    }

}
