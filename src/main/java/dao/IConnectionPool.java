package dao;

import java.sql.Connection;

public interface IConnectionPool {

    public Connection getConnection() throws DAOException;
}
