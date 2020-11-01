package dao.connection;

import dao.exception.DAOException;

import java.sql.Connection;

public interface IConnectionPool {

    public Connection getConnection() throws DAOException;
}
